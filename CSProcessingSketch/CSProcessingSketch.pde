import java.util.*;
import org.processing.wiki.triangulate.*;

float globeRad;
float phiRot = .3420;//.1892;
RentField path;


void setup()
{
  size(1200,700,P3D);
  globeRad = height*4.f;
  path = new RentField(this);
  path.parseFile("file (2).xml");
}

void draw()
{
  background(0);
      float cameraZ = ((height/2.0) / tan(PI*60.0/360.0));
    perspective(PI/8.0, width/height, .0001, cameraZ*10.0);
    
  float[] mid = path.getMidPt();
//  mid[0] = .344198;
  noFill();
  stroke(255);

  translate(width/2,height/2);
  float zDist = -2270;//+mouseY*550.f/height;
  translate(0,0,zDist);
  float yRot = 0.4404167 - mouseX*.05/width;
  phiRot =  .4110 - mouseY*.03f/height;
  println("yRot: "+yRot + ", zDist: " + zDist + " phiRot: " + phiRot);  
  
  rotateX(phiRot+mid[0]);
  //rotateX(phiRot);
//  rotateY(6+mouseX*.5/width);
  rotateY(mid[1] +yRot);
  
  //draw Longitude
  pushMatrix();
  ellipse(0,0,globeRad*2,globeRad*2);
  rotateZ(HALF_PI);
  for(int i = 0; i < 10; i++)
  {
    rotateX(TWO_PI/10);
    ellipse(0,0,globeRad*2,globeRad*2);
  }
  popMatrix();

  path.draw();
}
void vertex(double x, double y, double z) {
  vertex((float)x,(float)y,(float)z); 
}

void keyPressed()
{
  float phiDelta = .001;
  if( key == 'a')
  {
    phiRot += phiDelta;
  }
  if( key == 'z')
  {
    phiRot -= phiDelta;
  }
}
