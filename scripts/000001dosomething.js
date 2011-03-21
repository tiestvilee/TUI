var primitives = org.tiestvilee.tui.primitives;
image.keyPressed = function(keyEvent) {
  var white = new primitives.Colour(1.0, primitives.Hue.WHITE);
  var black = new primitives.Colour(0.0, primitives.Hue.BLACK);
  var colourPair = new primitives.ColourPair(white, black);
  var tixel = new primitives.Tixel(characterMap.get(new java.lang.Character(keyEvent.getKeyChar())), colourPair);
  var position = new primitives.Position(5,6);
  view.setPosition$To(position, tixel);
}
