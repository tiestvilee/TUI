
image.apps.textView.temp = {};
image.apps.textView.temp.instances = [beget(image.apps.textView.integration, {
    colourPair : new image.platform.newColourPair(new image.platform.newColour(1.0, org.tiestvilee.tui.primitives.Hue.WHITE), new image.platform.newColour(0.0, org.tiestvilee.tui.primitives.Hue.BLACK)),
    view : view,
//    view : view.clipTo(image.platform.newRectangle(5,5)).offsetBy(image.platform.newPosition(-5,-5)),
//    view : view.offsetBy(image.platform.newPosition(-5,-5)).clipTo(image.platform.newRectangle(10,10)),
    characterMap : characterMap,
    platform : image.platform,
    cursorPosition : image.platform.newPosition(0,0)
  })];

(function scope() {
  var currentTextView = image.apps.textView.temp.instances[0];
  currentTextView.command('load world.txt');
})();

image.apps.windowManager = { unit : {
    currentTarget : null,
    keyPressed : function(keyEvent) {
      this.currentTarget.keyPressed(keyEvent);
    },
    keyTyped : function(keyEvent) {
      this.currentTarget.keyTyped(keyEvent);
    },
    keyReleased : function(keyEvent) {
      if(this.currentTarget.keyReleased) {
        this.currentTarget.keyReleased(keyEvent);
      }
    }
  },
  temp : {}
};

image.apps.windowManager.temp.instance = beget(image.apps.windowManager.unit, {
  currentTarget : image.apps.textView.temp.instances[0]
});

image.eventHandler = {
  keyPressed : function(keyEvent) {
    image.apps.windowManager.temp.instance.keyPressed(keyEvent);
  },
  keyTyped : function(keyEvent) {
    image.apps.windowManager.temp.instance.keyTyped(keyEvent);
  },
  keyReleased : function(keyEvent) {
    image.apps.windowManager.temp.instance.keyReleased(keyEvent);
  }
};

