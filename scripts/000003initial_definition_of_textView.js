
image.components.scrollPane = (function() {
  var unit = {
    view : null,
    offsetBy : function(delta) {
      this.view.mutateBy(delta);
      this.view.redraw();
    },
    getView : function() {
      return this.view;
    }
  };

  var main = function(view) {

    var result = beget(unit, {
      view : view.offsetBy(image.platform.newPosition(0,0))
    })

    return result;
  }

  return {unit: unit, main: main};
}) ();

image.components.paneWithBorder = (function() {
  var unit = {
    title : null,
    view : null,
    childView : null,
    resize : function(newClip) {
      this.view.mutateTo(newClip);
      this.childView.mutateTo(image.platform.newRectangle(0, 1, newClip.width-1,newClip.height-1));
      this.gotRedraw();
    },
    gotRedraw : function() {
      var whiteOnBlack = image.platform.newColourPair(new image.platform.newColour(1.0, org.tiestvilee.tui.primitives.Hue.WHITE), new image.platform.newColour(0.0, org.tiestvilee.tui.primitives.Hue.BLACK))
      var grey = image.platform.newColourPair(new image.platform.newColour(1.0, org.tiestvilee.tui.primitives.Hue.GREEN), new image.platform.newColour(0.2, org.tiestvilee.tui.primitives.Hue.WHITE))
      var greyTx = image.platform.newTixel(' ', grey);

      for(x=0; x<this.title.length; x++) {
        this.view.setPosition$To(image.platform.newPosition(x, 0), image.platform.newTixel(this.title[x], grey));
      }
      for(; x<this.view.getClip().width; x++) {
        this.view.setPosition$To(image.platform.newPosition(x, 0), greyTx);
      }
      for(var y=1; y<this.view.getClip().height; y++) {
        this.view.setPosition$To(image.platform.newPosition(x-1, y), greyTx);
      }
    },

    getView : function() {
      return this.childView;
    }
  };

  var main = function(view, title, clipRect) {

    var clippedView = view.clipTo(clipRect).offsetBy(image.platform.newPosition(clipRect.x, clipRect.y));
    var innerClip = clippedView.clipTo(image.platform.newRectangle(0, 1, clipRect.width-1,clipRect.height-1));
    var result = beget(unit, {
      title : title,
      view : clippedView,
      childView : innerClip
    });
    clippedView.listenForRedraw(result);
    result.gotRedraw();

    return result;
  }

  return {unit: unit, main: main};
}) ();

image.components.textView = (function () {
  var textView = {};

  textView.unit = {
    colourPair : null,
    view : null,
    characterMap : null,
    platform : null,
    cursorPosition : null,
    model: null,


    write$AtCursorAndProceed : function(c) {
      var tixel = this.platform.newTixel(c, this.colourPair);
      this.view.setPosition$To(this.cursorPosition, tixel);
      this.cursorPosition = this.cursorPosition.offsetBy(this.platform.newPosition(1,0));
    },
    load : function(filename) {
      var br = this.platform.io.newBufferedReader(filename);
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
    },
    gotRedraw : function () {
      this.redraw();
    }
  };

  textView.integration = beget(textView.unit, {
    colourPair : image.platform.newColourPair(image.platform.newColour(1.0, org.tiestvilee.tui.primitives.Hue.WHITE), image.platform.newColour(0.0, org.tiestvilee.tui.primitives.Hue.BLACK)),
    platform : image.platform,
    cursorPosition : image.platform.newPosition(0,0),
    model: [[]],
  });

  textView.main = function(view) {
    var result = beget(textView.integration, {
      view : view
    });
    view.listenForRedraw(result);
    return result;
  }

  return textView;
}) ();

/*

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
    */