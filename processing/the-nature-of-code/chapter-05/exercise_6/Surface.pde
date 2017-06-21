class Surface {

  ArrayList<Vec2> surface;

  Surface() {
    surface = new ArrayList<Vec2>();
    ChainShape chain = new ChainShape();
    float theta = 0;

    for (float x = width+10; x > -10; x -= 5) {
      float y = map(cos(theta), -1, 1, 75, height-10);
      theta += 0.15;
      surface.add(new Vec2(x, y));
    }

    Vec2[] vertices = new Vec2[surface.size()];
    for (int i = 0; i < vertices.length; i++) {
      Vec2 edge = box2d.coordPixelsToWorld(surface.get(i));
      vertices[i] = edge;
    }

    chain.createChain(vertices, vertices.length);

    BodyDef bd = new BodyDef();
    bd.position.set(0.0f, 0.0f);
    Body body = box2d.createBody(bd);
    body.createFixture(chain, 1);
  }

  void display() {
    strokeWeight(2);
    stroke(0);
    noFill();

    beginShape();
    for (Vec2 v : surface) {
      vertex(v.x, v.y);
    }
    endShape();
  }
}