package com.fenyx.render;


public final class TextureFormat
{
  public int format;
  
  public int internal_format;
  
  public int wrapping;
  
  public int filtration;
  
  public boolean has_alpha;
  
  private static TextureFormat defaultFormat;
  private static TextureFormat defaultAlpha;
  
  public static TextureFormat getDefault()
  {
    if (defaultFormat == null) {
      defaultFormat = new TextureFormat();
      defaultFormat.internal_format = 32849;
      defaultFormat.format = 6407;
      defaultFormat.wrapping = 33071;
      defaultFormat.filtration = 9729;
      defaultFormat.has_alpha = false;
    }
    
    return defaultFormat;
  }
  
  public static TextureFormat getDefaultAlpha() {
    if (defaultAlpha == null) {
      defaultAlpha = new TextureFormat();
      defaultAlpha.internal_format = 32856;
      defaultAlpha.format = 6408;
      defaultAlpha.wrapping = 33071;
      defaultAlpha.filtration = 9729;
      defaultAlpha.has_alpha = true;
    }
    
    return defaultAlpha;
  }
}