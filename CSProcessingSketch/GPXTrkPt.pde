class GPXTrkPt implements Comparable
{
  public float lon;
  public float lat;
  public float ele;
  public Date time;
  
  public GPXTrkPt(float latitude, float longitude, float elevation)
  {
    this(latitude, longitude, elevation,new Date());
  }
  
  public GPXTrkPt(float latitude, float longitude, float elevation, Date date)
  {
    lon = longitude;
    lat = latitude;
    ele = elevation;
    time = date;
  } 
    
  int compareTo(Object obj)
  {
    
    return 1;
  }
}
