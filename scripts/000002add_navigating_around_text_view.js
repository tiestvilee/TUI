var textView = image.apps.textView;

image.eventHandler.keyTyped = function(keyEvent) {
  textView.instance.keyTyped(keyEvent);
}
image.eventHandler.keyReleased = function(keyEvent) {}

textView.unit.cursorPosition = null;

textView.unit.write$AtCursorAndProceed = function(c) {
  var tixel = this.platform.newTixel(characterMap.get(c), this.colourPair);
  this.view.setPosition$To(this.cursorPosition, tixel);
  this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(1,0));
}

textView.unit.keyTyped = function(keyEvent) {
  var keyChar = keyEvent.getKeyChar();
  if(keyChar > 31) {
    this.write$AtCursorAndProceed(keyChar);
  }
}

textView.unit.keyPressed = function (keyEvent) {

}

textView.instance.cursorPosition = image.platform.newPosition(5,5);