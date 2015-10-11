package com.andela.www.currencycalculator;

/**
 * Created by kamiye on 10/9/15.
 */
public class CurrencyModel {

    private  String CompanyName="";
    private  String Image="";
    private  String Url="";

    /*********** Set Methods ******************/
    public void setCurrencyName(String CompanyName)
    {
        this.CompanyName = CompanyName;
    }

    public void setImage(String Image)
    {
        this.Image = Image;
    }

    public void setUrl(String Url)
    {
        this.Url = Url;
    }

    /*********** Get Methods ****************/
    public String getCurrencyName()
    {
        return this.CompanyName;
    }

    public String getImage()
    {
        return this.Image;
    }

    public String getUrl()
    {
        return this.Url;
    }
}
