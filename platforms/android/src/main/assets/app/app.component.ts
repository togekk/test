import { Component } from "@angular/core";

@Component({
    selector: "my-app",
    templateUrl: "app.component.html",
})
export class AppComponent {
    // public counter: number = 16;
    public name: string
    public message: string
    public aaaa: any

    // public get message(): string {
    //     if (this.counter > 0) {
    //         return this.counter + " taps left";
    //     } else {
    //         return "Hoorraaay! \nYou are ready to start building!";
    //     }
    //     return "Your name is"
    // }

    public onTap() {
      // console.log(this.aaa)
      this.message = "Your name is " + this.name
    }
}
