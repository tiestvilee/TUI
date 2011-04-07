var textView = {};

textView.unit = {
  colourPair : null,
  view : null,
  characterMap : null,
  platform : null,
  cursorPosition : null,

  keyPressed : function(keyEvent) {
  },
  write$AtCursorAndProceed : function(c) {
    var tixel = this.platform.newTixel(characterMap.get(c), this.colourPair);
    this.view.setPosition$To(this.cursorPosition, tixel);
    this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(1,0));
  },
  keyTyped : function(keyEvent) {
    var keyChar = keyEvent.getKeyChar();
    if(keyChar > 31) {
      this.write$AtCursorAndProceed(keyChar);
    }
  }
};

textView.integration = beget(textView.unit);

image.apps.textView = textView;
