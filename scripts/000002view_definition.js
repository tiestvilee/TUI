
image.platform.view = {

  redrawListeners : [],
  view : view,
  emptyTixel : view.emptyTixel,

  setPosition$To : function(position, tixel) {
    this.view.setPosition$To(position, tixel);
  },
  clearPositionAt : function(position) {
    this.view.clearPositionAt(position);
  },
  getTixelAt : function(position) {
    return this.view.getTixelAt(position);
  },
  forEachElementDo : function(elementAction) {
    throw "don't know how to do this";
  },
  getClip : function() {
    return this.view.getClip();
  },

  writeContentsTo : function(target) {
    this.forEachElementDo(function(position, tixel) {
            target.setPosition$To(position, tixel);
        }
    );
  },

  redraw : function() {
    this.redrawListeners.forEach(function(listener) {
        listener.gotRedraw();
    });
  },

  listenForRedraw : function(redrawListener) {
    this.redrawListeners.push(redrawListener);
  },

  offsetBy : function(offset) {
    var underlying = this;
    return beget(this, {
      setPosition$To : function(position, tixel) {
        underlying.setPosition$To(position.offsetBy(offset), tixel);
      },
      clearPositionAt : function(position) {
        underlying.clearPositionAt(position.offsetBy(offset));
      },
      getTixelAt : function(position) {
        return underlying.getTixelAt(position.offsetBy(offset));
      },
      forEachElementDo : function(elementAction) {
        underlying.forEachElementDo(function(position, tixel) {
            target.setPosition$To(position.offsetBy(offset.negate()), tixel);
          }
        );
      },
      getClip : function() {
        return underlying.getClip().offsetBy(offset.negate());
      },
      mutateBy : function(position) {
        offset = offset.offsetBy(position);
      },
      gotRedraw : function() {
          redraw();
      }
    });
  },

  clipTo : function(clipRect) {
    var underlying = this;
    return beget(this, {
      setPosition$To : function(position, tixel) {
        if(clipRect.contains(position)) {
          underlying.setPosition$To(position, tixel);
        }
      },
      clearPositionAt : function(position) {
        if(clipRect.contains(position)) {
          underlying.clearPositionAt(position);
        }
      },
      getTixelAt : function(position) {
        if(clipRect.contains(position)) {
          return underlying.getTixelAt(position);
        } else {
          return this.emptyTixel;
        }
      },
      forEachElementDo : function(elementAction) {
        underlying.forEachElementDo(function(position, tixel) {
            if(clipRect.contains(position)) {
              target.setPosition$To(position, tixel);
            }
          }
        );
      },
      getClip : function() {
        return clipRect.intersectWith(underlying.getClip());
      },
      mutateTo : function(newClip) {
        clipRect = newClip;
      },
      gotRedraw : function() {
          redraw();
      }
    });
  },

}
