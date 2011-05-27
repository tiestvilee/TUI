

function beget(parent, instanceValues) {
  var fn = function() {};
  fn.prototype = parent;
  var result = new fn();
  if(instanceValues) {
    for(key in instanceValues) {
      if(instanceValues.hasOwnProperty(key)) {
        result[key] = instanceValues[key];
      }
    }
  }
  return result;
}

image.platform = {
  newTixel : function(c, colourPair) {
    return new org.tiestvilee.tui.primitives.Tixel(c, colourPair);
  },
  newPosition : function(x, y) {
    return new org.tiestvilee.tui.primitives.Position(x,y);
  },
  newRectangle : function(x, y, width, height) {
    if(!width) {
      return new org.tiestvilee.tui.primitives.Rectangle(x, y);
    }
    return new org.tiestvilee.tui.primitives.Rectangle(x, y, width, height);
  },
  newColourPair : function(fore, back) {
    return new org.tiestvilee.tui.primitives.ColourPair(fore, back);
  },
  newColour : function(intensity, hue) {
    return new org.tiestvilee.tui.primitives.Colour(intensity, hue);
  },
  keymappings : {
    PAGE_UP : 33,
    PAGE_DOWN : 34,
    END : 35,
    HOME : 36,
    
    LEFT : 37,
    UP : 38,
    RIGHT : 39,
    DOWN : 40,
    
    SHIFT : 16,
    CTRL : 17,
    ALT : 18,
    CAPS : 20,
    ESC : 27,

    INSERT : 155,
    BACKSPACE : 8,
    DELETE : 127,
    ENTER : 10
  },
  io : {
    newBufferedReader : function(filename) {
      var fr = new java.io.FileReader(filename);
      var br = new java.io.BufferedReader(fr);
      return br;
    }
  }
};

image.components = {};
image.apps = {};
image.instances = {};
