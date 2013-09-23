class GPXPath
{
  float maxPrice = 0;
  ArrayList<GPXTrkPt> ptList;
  PApplet appletPtr;
  public GPXPath(PApplet app)
  {
    appletPtr = app;
    ptList = new ArrayList<GPXTrkPt>();    
  }
  
  public void parseFile(String file)
  {
    XMLElement day1 = new XMLElement(appletPtr, file);  
    int numTrks = day1.getChildCount();
    //    day->trk->trkseg->trkpt->lat
    XMLElement[] trks = day1.getChildren();
    
     for (int i = 0; i < numTrks; i++) 
    {
   
          float lat = trks[i].getFloat("lat");
          float lon = trks[i].getFloat("lon");
          float ele = trks[i].getFloat("cost");
          String pid = trks[i].getString("pid");
//          println(pid);
maxPrice = max(maxPrice,ele);
          ptList.add(new GPXTrkPt(lat,lon,ele));    
    }
    maxPrice = 2000;
    println("maxPrice: " + maxPrice);
    println("ptList.size(): " + ptList.size());
  }
  
  public void draw()
  {
    stroke(255);
    noFill();
//    beginShape();
    for(GPXTrkPt pt : ptList)
    {      
      pushMatrix();
        float r = globeRad * (0 + 6367.47000) / 6367.47000;
//        float r = globeRad * (pt.ele*10/maxPrice + 6367.47000) / 6367.47000;
        double x = r * Math.sin(pt.lat*PI/180) * Math.cos(pt.lon*PI/180);
        double y = r * Math.sin(pt.lat*PI/180) * Math.sin(pt.lon*PI/180);
        double z = r * Math.cos(pt.lat*PI/180);
        translate((float)x,(float)y,(float)z);
//        vertex(x,y,z);
stroke(pt.ele*255/maxPrice,255,0);
float radus = .5- pt.ele*.49/maxPrice;
ellipse(0,0,radus,radus);
        popMatrix();
    }  
//    endShape();
  }
  
  //returns 'averaged' values from all points in the list
  public float[] getMidPt()
  {
    float[] mid = new float[4];
    for(GPXTrkPt pt : ptList)
    {
      mid[0] += pt.lat;
      mid[1] += pt.lon;
      mid[2] += pt.ele;
      mid[3] += pt.time.getTime();
    }
    int count = ptList.size();
    mid[0] /= count; mid[1] /= count; mid[2] /= count; mid[3] /= count;
    return mid;
  }
}
