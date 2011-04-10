
image.instances.textView = beget(image.apps.textView.integration, {
    colourPair : new image.platform.newColourPair(new image.platform.newColour(1.0, org.tiestvilee.tui.primitives.Hue.WHITE), new image.platform.newColour(0.0, org.tiestvilee.tui.primitives.Hue.BLACK)),
    view : view.clipTo(image.platform.newRectangle(5,5)).offsetBy(image.platform.newPosition(-5,-5)),
//    view : view.offsetBy(image.platform.newPosition(-5,-5)).clipTo(image.platform.newRectangle(10,10)),
    characterMap : characterMap,
    platform : image.platform,
    cursorPosition : image.platform.newPosition(5,5)
  });

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
  }
};

image.instances.windowManager = beget(image.apps.windowManager.unit, {
  currentTarget : image.instances.textView
})

image.eventHandler = {
  keyPressed : function(keyEvent) {
    image.instances.windowManager.keyPressed(keyEvent);
  },
  keyTyped : function(keyEvent) {
    image.instances.windowManager.keyTyped(keyEvent);
  },
  keyReleased : function(keyEvent) {
    image.instances.windowManager.keyReleased(keyEvent);
  }
};

