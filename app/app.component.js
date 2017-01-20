"use strict";
var core_1 = require("@angular/core");
var AppComponent = (function () {
    function AppComponent() {
    }
    // public get message(): string {
    //     if (this.counter > 0) {
    //         return this.counter + " taps left";
    //     } else {
    //         return "Hoorraaay! \nYou are ready to start building!";
    //     }
    //     return "Your name is"
    // }
    AppComponent.prototype.onTap = function () {
        // console.log(this.aaa)
        this.message = "Your name is " + this.name;
    };
    AppComponent = __decorate([
        core_1.Component({
            selector: "my-app",
            templateUrl: "app.component.html",
        }), 
        __metadata('design:paramtypes', [])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map