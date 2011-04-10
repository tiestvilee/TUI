image.instances.textView = beget(image.apps.textView.integration, {
    colourPair : new image.platform.primitives.ColourPair(new image.platform.primitives.Colour(1.0, image.platform.primitives.Hue.WHITE), new image.platform.primitives.Colour(0.0, image.platform.primitives.Hue.BLACK)),
    view : view.clipTo(image.platform.newRectangle(5,5)).offsetBy(image.platform.newPosition(-5,-5)),
//    view : view.offsetBy(image.platform.newPosition(-5,-5)).clipTo(image.platform.newRectangle(10,10)),
    characterMap : characterMap,
    platform : image.platform,
    cursorPosition : image.platform.newPosition(5,5)
  });

image.eventHandler = {
  keyPressed : function(keyEvent) {
    image.instances.textView.keyPressed(keyEvent);
  }
};


image.eventHandler.keyTyped = function(keyEvent) {
  image.instances.textView.keyTyped(keyEvent);
}
image.eventHandler.keyReleased = function(keyEvent) {}