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
  }
};

image.unit = {
  textView : {
    colourPair : null,
    view : null,
    characterMap : null,
    platform : null,

    keyPressed : function(keyEvent) {
      var tixel = this.platform.newTixel(characterMap.get(keyEvent.getKeyChar()), this.colourPair);
      var position = this.platform.newPosition(5,6);
      this.view.setPosition$To(position, tixel);
    }
  }
};

image.instance = {
  textView : beget(image.unit.textView, {
    colourPair : new image.platform.primitives.ColourPair(new image.platform.primitives.Colour(1.0, image.platform.primitives.Hue.WHITE), new image.platform.primitives.Colour(0.0, image.platform.primitives.Hue.BLACK)),
    view : view,
    characterMap : characterMap,
    platform : image.platform,
  })
};

image.eventHandler = {
  keyPressed : function(keyEvent) {
    image.instance.textView.keyPressed(keyEvent);
  }
};
