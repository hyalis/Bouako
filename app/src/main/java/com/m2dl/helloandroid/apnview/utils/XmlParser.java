package com.m2dl.helloandroid.apnview.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class XmlParser
{
    static Document document;
    static Element racine;
    private Context context;
    private String LOG_TAG = "XMLPARSER";

    public XmlParser(Context context)
    {
        this.context = context;
        //On crée une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            Log.d("file" , context.getFilesDir().getPath());
            AssetManager assetManager = context.getResources().getAssets();
            InputStream file = assetManager.open("imageTypes.xml");
            OutputStream out = null;

            try {
                out = new FileOutputStream(context.getFilesDir().getPath() + "/imageTypes.xml");

                byte[] buffer = new byte[65536 * 2];
                int read;
                while ((read = file.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                file.close();
                file = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                Log.e("TEST", "ERROR: " + e.toString());
            }


            //On crée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            //File fileXml = new File(Environment.getExternalStorageDirectory() + File.separator + "imageTypes.xml");

            File fileXml = new java.io.File(context.getFilesDir().getPath()+ "/imageTypes.xml");

            document = sxb.build(fileXml);
        }
        catch(Exception e){
            Log.e("TEST", "ERROR: " + e.toString());

        }
        //On initialise un nouvel élément racine avec l'élément racine du document.
        racine = document.getRootElement();
    }

    // Parse le fichier xml, et retourne les types, et sous types associés.
    public ArrayList<String> getTypesAndSousTypes()
    {
        //On crée une List contenant tous les noeuds "etudiant" de l'Element racine
        List listTypes = racine.getChildren("type");

        // En théorie nous avons que 2 types dans le fichier xml (voir doc)
        if(listTypes.size() > 2)
        {
            Log.e("XmlParser", "LE FICHIER XML D'ENTREE NE RESPECTE PAS LA DOC (2 types maxi !) ");
            Toast.makeText(context, "LE FICHIER XML D'ENTREE NE RESPECTE PAS LA DOC (2 types maxi !) ", Toast.LENGTH_LONG).show();
        }

        //Premier type et ses sous types:
        Element type1 = (Element) listTypes.get(0);
        Element type2 = (Element) listTypes.get(1);

        String nomType1 = type1.getAttribute("title").getValue();
        String nomType2 = type2.getAttribute("title").getValue();

        List sousTypes1 = type1.getChildren();
        List sousTypes2 = type2.getChildren();

        if(sousTypes1.size() > 2 || sousTypes2.size() > 2)
        {
            Log.e("XmlParser", "LE FICHIER XML D'ENTREE NE RESPECTE PAS LA DOC (2 soustypes par type maxi !) ");
            Toast.makeText(context, "LE FICHIER XML D'ENTREE NE RESPECTE PAS LA DOC (2 soustypes par type maxi !) ", Toast.LENGTH_LONG).show();

        }

        String nomSousType11 = ((Element) sousTypes1.get(0)).getAttribute("title").getValue();
        String nomSousType12 = ((Element) sousTypes1.get(1)).getAttribute("title").getValue();
        String nomSousType21 = ((Element) sousTypes2.get(0)).getAttribute("title").getValue();
        String nomSousType22 = ((Element) sousTypes2.get(1)).getAttribute("title").getValue();

        ArrayList<String> listTypesAndSousTypes = new ArrayList<String>();
        listTypesAndSousTypes.add(nomType1);
        listTypesAndSousTypes.add(nomType2);
        listTypesAndSousTypes.add(nomSousType11);
        listTypesAndSousTypes.add(nomSousType12);
        listTypesAndSousTypes.add(nomSousType21);
        listTypesAndSousTypes.add(nomSousType22);

        Log.d("XMLPARSER", "LISTE DES TYPES : \n"+ listTypesAndSousTypes.toString());
        return listTypesAndSousTypes;
    }


}