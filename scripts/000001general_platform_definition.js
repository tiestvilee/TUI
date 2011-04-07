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
  primitives : org.tiestvilee.tui.primitives,
  newTixel : function(c, colourPair) {
    return new this.primitives.Tixel(c, colourPair);
  },
  newPosition : function(x, y) {
    return new this.primitives.Position(x,y);
  },
  newRectangle : function(width, height) {
    return new this.primitives.Rectangle(width, height);
  }
};

image.apps = {};
image.instances = {};
