package com.tns;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

public class NativeScriptUncaughtExceptionHandler implements UncaughtExceptionHandler
{
	private final Context context;

	private final UncaughtExceptionHandler defaultHandler;

	private final Logger logger;
	
	public NativeScriptUncaughtExceptionHandler(Logger logger, Context context)
	{
		this.logger = logger;
		this.context = context;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		String currentThreadMessage = "An uncaught Exception occurred on \"" + thread.getName() + "\" thread.\n";

		String errorMessage = currentThreadMessage + getErrorMessage(ex);
		
		if (Runtime.isInitialized())
		{
			try
			{
				ex.printStackTrace();
				
				Runtime runtime = Runtime.getCurrentRuntime();

				if (runtime != null)
				{
					runtime.passUncaughtExceptionToJs(ex, errorMessage);
				}
				
				if (JsDebugger.isJsDebuggerActive())
				{
					return;
				}
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}

		if (logger.isEnabled())
		{
			logger.write("Uncaught Exception Message=" + errorMessage);
		}

		boolean res = false;

		if (AndroidJsDebugger.isDebuggableApp(context)) {
			Class ErrReport = null;
			java.lang.reflect.Method startActivity = null;

			try {
				ErrReport = java.lang.Class.forName("com.tns.ErrorReport");
			} catch (ClassNotFoundException e) {
				android.util.Log.d("ClassNotFoundException", e.toString());
			}

			try {
				startActivity = ErrReport.getDeclaredMethod("startActivity", android.content.Context.class, String.class);
			} catch (NoSuchMethodException e) {
				android.util.Log.d("NoSuchMethodException", e.toString());
			}

			try {
				res = (Boolean) startActivity.invoke(null, context, errorMessage);
			} catch (Exception e) {
				android.util.Log.d("Exception", e.toString());
			}
		}

		if (!res && defaultHandler != null)
		{
			defaultHandler.uncaughtException(thread, ex);
		}
	}

	private static String getErrorMessage(Throwable ex) {
		String content;
		java.io.PrintStream ps = null;

		try {
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			ps = new java.io.PrintStream(baos);
			ex.printStackTrace(ps);

			try {
				content = baos.toString("US-ASCII");
			} catch (java.io.UnsupportedEncodingException e) {
				content = e.getMessage();
			}
		} finally {
			if (ps != null)
				ps.close();
		}

		return content;
	}
}
