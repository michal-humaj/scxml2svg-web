# Package SvgComposer #

Package SvgComposer


# Classes #

  * [#Bounds](#Bounds.md)
  * [#Collide](#Collide.md)
  * [#Size](#Size.md)
  * [#StateLayout](#StateLayout.md)
  * [#StateContainer](#StateContainer.md)
  * [#SvgComposer](#SvgComposer.md)
  * [#TextUtils](#TextUtils.md)
  * [#TransitionArrow](#TransitionArrow.md)
  * [#Vector](#Vector.md)


---


## Bounds ##

_Bounds class facilitates bounding boxes._

**Attributes**

  * private double minx
  * private double miny
  * private double maxx
  * private double maxy

#### public Bounds() ####
  * Creates a zero-width bounding box.

#### public void update(double x, double y) ####
_Updates the box with new coordinates._
If they're out of current bounds, the rectangle expands to fit them in.
  * @param x
  * @param y

#### public double getMinX() ####
_Gets the left side boundary_
  * @return minx

#### public double getMinY() ####
_Gets the top side boundary_
  * @return miny

#### public double getMaxX() ####
_Gets the right side boundary_
  * @return maxx

#### public double getMaxY() ####
_Gets the bottom side boundary_
  * @return maxy

## Collide ##

## Size ##
_Two-dimensional immutable size primitive._

**Attributes**
  * private double width
  * private double height

#### public Size(double width, double height) ####
_Creates a size with given width and height._
  * @param width
  * @param height

#### public double getWidth() ####
_Returns the size's width_
  * @return width

#### public double getHeight() ####
_Returns the size's height_
  * @return height

## StateContainer ##

## StateLayout ##

## SvgComposer ##

## TextUtils ##

## TransitionArrow ##

## Vector ##