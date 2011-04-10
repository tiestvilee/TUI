
var textView = {};

textView.unit = {
  colourPair : null,
  view : null,
  characterMap : null,
  platform : null,
  cursorPosition : null,

  keyPressed : function(keyEvent) {
    var keymappings = this.platform.keymappings;
    switch(keyEvent.getKeyCode()) {
      case keymappings.LEFT :
          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(-1, 0));
        break;
      case keymappings.RIGHT :
          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(1, 0));
        break;
      case keymappings.UP :
          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(0, -1));
        break;
      case keymappings.DOWN :
          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(0, 1));
        break;
    }
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
    } else {
      var keymappings = this.platform.keymappings;
      switch(keyEvent.getKeyCode()) {
        case keymappings.ENTER :
            this.cursorPosition = this.platform.newPosition(0, this.cursorPosition.y + 1);
          break;
        case keymappings.BACKSPACE :
            this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(-1, 0));
            this.view.clearPositionAt(this.cursorPosition);
          break;
      }
    }
  }
};

textView.integration = beget(textView.unit);

image.apps.textView = textView;
