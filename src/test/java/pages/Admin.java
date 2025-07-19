package pages;

import testbase.TestBase;

import static utilities.Utilities.*;

public class Admin extends TestBase {
    public void corporateBranding(){
        onClick("corporateBranding");
        uploadFileToWeb("clientLogoBrowse", "clientLogo");

    }
}
