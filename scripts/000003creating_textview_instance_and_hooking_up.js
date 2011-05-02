

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

image.apps.fileViewer = (function() {
  var unit = {
    keymappings : null,
    scrollView : null,
    keyPressed : function(keyEvent) {
      switch(keyEvent.getKeyCode()) {
        case this.keymappings.LEFT :
            this.scrollView.offsetBy(image.platform.newPosition(-1, 0));
          break;
        case this.keymappings.RIGHT :
            this.scrollView.offsetBy(image.platform.newPosition(1, 0));
          break;
        case this.keymappings.UP :
            this.scrollView.offsetBy(image.platform.newPosition(0, -1));
          break;
        case this.keymappings.DOWN :
            this.scrollView.offsetBy(image.platform.newPosition(0, 1));
          break;
      }
    },
    keyTyped : function () {}
  };

  var integration = beget(unit, {
    keymappings : image.platform.keymappings
  })

  main = function(view, fileName) {
    var scrollView = view.offsetBy(image.platform.newPosition(0,0));
    var currentScrollView = image.components.scrollPane.main(view);
    var currentTextView = image.components.textView.main(currentScrollView.view);
    currentTextView.command('load ' + fileName);

    return beget(integration, {scrollView : currentScrollView});
  }

  return {unit : unit, integration : integration, main : main};
}) ();


image.apps.windowManager.temp.instance = beget(image.apps.windowManager.unit, {
  currentTarget : image.apps.fileViewer.main(view.clipTo(image.platform.newRectangle(110,24)), "hello.txt")
});
image.apps.fileViewer.main(view.offsetBy(image.platform.newPosition(0,24)).clipTo(image.platform.newRectangle(110,24)), "hello.txt")

