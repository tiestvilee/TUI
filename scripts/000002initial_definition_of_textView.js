
var textView = {};

textView.unit = {
  colourPair : null,
  view : null,
  characterMap : null,
  platform : null,
  cursorPosition : null,
  model: [],


  write$AtCursorAndProceed : function(c) {
    var tixel = this.platform.newTixel(characterMap.get(c), this.colourPair);
    this.view.setPosition$To(this.cursorPosition, tixel);
    this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(1,0));
  },
  keyPressed : function(keyEvent) {
    var keymappings = this.platform.keymappings;
    switch(keyEvent.getKeyCode()) {
      case keymappings.LEFT :
          this.view = this.view.offsetBy(this.platform.newPosition(-1, 0));
          this.redraw();
//          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(-1, 0));
        break;
      case  keymappings.RIGHT :
          this.view = this.view.offsetBy(this.platform.newPosition(1, 0));
          this.redraw();
//          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(1, 0));
        break;
      case keymappings.UP :
          this.view = this.view.offsetBy(this.platform.newPosition(0, -1));
          this.redraw();
//          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(0, -1));
        break;
      case keymappings.DOWN :
//          this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(0, 1));
          this.view = this.view.offsetBy(this.platform.newPosition(0, 1));
          this.redraw();
        break;
    }
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
  },
  load : function(filename) {
    var fr = new java.io.FileReader(filename);
    var br = new java.io.BufferedReader(fr);
    var line;
    while(true) {
      var line = br.readLine();
      if(line == null) {
        break;
      }
      this.model.push(new String(line));
    }
    br.close();

    this.redraw();
  },
  redraw : function() {
    var endPoint = this.model.length;
    var clip = this.view.getClip();
    if(endPoint > (clip.y + clip.height)) {
      endPoint = clip.y + clip.height;
    } else {
      this.cursorPosition = this.platform.newPosition(0, endPoint);
      for(var i=0; i<this.model[this.model.length - 1].length; i++) {
        this.write$AtCursorAndProceed(' ');
      }
    }
    var startPoint = 0;
    if(clip.y > 0) {
      startPoint = clip.y;
    } else {
      this.cursorPosition = this.platform.newPosition(0, -1);
      for(var i=0; i<this.model[0].length; i++) {
        this.write$AtCursorAndProceed(' ');
      }
    }
    for(var j=startPoint; j<endPoint; j++){
      this.cursorPosition = this.platform.newPosition(-1, j);
      this.write$AtCursorAndProceed(' ');
      var line = this.model[j];
      var i=0;
      for(; i<line.length; i++) {
        this.write$AtCursorAndProceed(line[i]);
      }
      var fillTo = i+1;
      if(this.model[j-1] && this.model[j-1].length > fillTo) {
        fillTo = this.model[j-1].length;
      }
      if(this.model[j+1] && this.model[j+1].length > fillTo) {
        fillTo = this.model[j+1].length;
      }
      for(; i<fillTo; i++) {
        this.write$AtCursorAndProceed(' ');
      }

    }

  },
  command : function(command) {
    if(/^load.*/.test(command)) {
      this.load(/^load\s+(\S+)/.exec(command)[1])
    }
  }
};

textView.integration = beget(textView.unit);

image.apps.textView = textView;
