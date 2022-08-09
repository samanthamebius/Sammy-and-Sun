package com.example.se306_project1.data;

import android.util.Log;
import android.webkit.WebHistoryItem;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClutchesDataProvider {

    // Add number documents to Firestore
    public static void addClutchestoFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<IProduct> clutchesList = getClutches();
        for (IProduct aClutch : clutchesList) {
            db.collection("clutches").document("clutch" + aClutch.getProductID()).set(aClutch).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("Clutches Collection Add", "clutch " + aClutch.getBrandName() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.w("Clutches Collection Add", "clutch " + aClutch.getBrandName() + " NOT added.");
                }
            });
        }
    }

    public static List<IProduct> getClutches() {
        List<IProduct> bagsList = new ArrayList<>();

        Map<Integer, String> brands = generateBrand();
        Map<Integer, String> longNames = generateLongName();
        Map<Integer, String> shortNames = generateShortName();
        Map<Integer, String> descriptions = generateDescription();
        Map<Integer, String> details = generateDetails();
        Map<Integer, String> productCares = generateProductCare();
        Map<Integer, Double> prices = generatePrice();
        Map<Integer, String> colourTypes = generateColourType();

        for (Integer key : brands.keySet()) {

            int productID = key;
            int categoryID = 1;

            Double productPrice = prices.get(key);
            String productLongName = longNames.get(key);
            String productShortName= shortNames.get(key);

            ArrayList<String> productImages = new ArrayList<>();
            productImages.add("clutch"+key+"_01.png");
            productImages.add("clutch"+key+"_02.png");
            productImages.add("clutch"+key+"_03.png");

            String productDescription = descriptions.get(key);
            String productDetails = details.get(key);
            String productCare = productCares.get(key);
            String brandName = brands.get(key);
            String productColourType = colourTypes.get(key);


            Product c = new Product(productID, categoryID, productPrice, productLongName, productShortName, productImages, productDescription, productDetails, productCare, brandName, productColourType);
            bagsList.add(c);
        }

        return bagsList;
    }

    public static Map<Integer, String> generateBrand() {
        Map<Integer, String> brands =
                new LinkedHashMap<Integer, String>();
        brands.put(1, "Gucci");
        brands.put(2, "Gucci");
        brands.put(3, "Prada");
        brands.put(4, "Louis Vuitton");
        brands.put(5, "Louis Vuiton");
        brands.put(6, "Coach");
        brands.put(7, "Dior");
        brands.put(8, "Fendi");
        brands.put(9, "Saint Laurant");
        brands.put(10, "Saint Laurant");
        return brands;
    }

    public static Map<Integer, String> generateLongName() {
        Map<Integer, String> longNames =
                new LinkedHashMap<Integer, String>();
        longNames.put(1, "Horsebit 1955 Small Bag");
        longNames.put(2, "Blondie Shoulder Bag");
        longNames.put(3, "Leather Triangle Pouch");
        longNames.put(4, "Felicie Pochette");
        longNames.put(5, "Easy Pouch On Strap");
        longNames.put(6, "Tabby Chain Clutch In Signature Jacquard");
        longNames.put(7, "Caro Macrocannage Pouch");
        longNames.put(8, "First Small Leather Bag");
        longNames.put(9, "Sade Large Clutch in Satin");
        longNames.put(10, "Cassandre Matelasse Flap Pouch");
        return longNames;
    }

    public static Map<Integer, String> generateShortName() {
        Map<Integer, String> shortNames =
                new LinkedHashMap<Integer, String>();
        shortNames.put(1, "Horsebit 1955");
        shortNames.put(2, "Blondie");
        shortNames.put(3, "Triangle Pouch");
        shortNames.put(4, "Felicie Pochette");
        shortNames.put(5, "Easy Pouch");
        shortNames.put(6, "Tabby");
        shortNames.put(7, "Caro Macrocannage");
        shortNames.put(8, "First");
        shortNames.put(9, "Sade");
        shortNames.put(10, "Cassandre Matelasse");
        return shortNames;
    }

    public static Map<Integer, String> generateDescription() {
        Map<Integer, String> descriptions =
                new LinkedHashMap<Integer, String>();
        descriptions.put(1, "Embodying the notion of the versatility, the Gucci Horsebit 1955 is enriched with two detachable shoulder straps, one is crafted in emblematic green and red Web the other is in Gucci’s recognisable gold-toned chain design. The style is crafted from white leather and defined by the line's signature Horsebit hardware.");
        descriptions.put(2, "Vintage elements are paired with archival details as an ode to the glamour that permeated the Gucci Love Parade fashion show. This shoulder bag pairs a delicate chain strap with GG Supreme canvas to infuse the accessory with a timeless feel. Reintroduced in honour of the collection, the rounded silhouette is completed by a historical, rounded iteration of Guccio Gucci's monogram.");
        descriptions.put(3, "A study of the triangle inspires new geometric elements and novel interpretations of Prada's historic stylistic code. The iconic shape is presented again in this sleek pouch that comes with a strap for versatility. The tonal embossed logo and the enameled metal triangle logo enhance the minimalist aesthetics.");
        descriptions.put(4, "The Pochette Felicie is crafted from Monogram Empreinte leather, embossed with Louis Vuitton's signature Monogram pattern. Designed to adapt to modern lifestyles, this envelope-style pouch contains a spacious compartment with two removable pockets: one zipped, one with card slots. It can also be carried in different ways thanks to a removable gold-tone chain.");
        descriptions.put(5, "For Spring 2022, the Easy Pouch On Strap is made from Monogram Empreinte leather. The sleek design lends itself beautifully to the embossed Monogram pattern on the supple Empreinte leather. It can be worn on the shoulder, with the chain or the strap for a short carry, or cross-body as the leather strap is adjustable.");
        descriptions.put(6, "A modern take on an archival 1970s Coach design, our structured Tabby clutch is crafted of our environmentally conscious Signature jacquard made from a blend of organic cotton and recycled plastic bottles--all part of our commitment to rethinking and reducing our impact on the planet by repurposing waste materials. Featuring our Signature hardware, this organized, three compartment design has a secure interior zippered compartment and an exterior slip pocket for easy access to essentials.");
        descriptions.put(7, "This season's new Dior Caro pouch showcases the House's couture spirit with a practical design. Crafted in azure blue calfskin, it stands out with a unique Macrocannage quilted effect. The flap has an antique gold-finish metal 'CD' signature, and reveals compartments to hold all the essentials.");
        descriptions.put(8, "Small Fendi First bag made of soft, lilac nappa leather with oversized metal F clasp bound in tone on tone nappa leather. Featuring an interior compartment lined in fabric with the iconic FF motif, removable inner hooks and gold-finish metalware. Can be carried by hand as a clutch or worn on the shoulder thanks to the detachable shoulder strap. Made in Italy.");
        descriptions.put(9, "Puffer envelope clutch with magnetic front flap. Decorated with all-over quilted overstitching and metal YSL initials at the back.");
        descriptions.put(10, "Monogram envelpe cliutch with a front flap, made with metal free tanned leather and organic cotton lining and decorated with metal YSL initials and chevron quilted overstitching, featuring a detachable wristlit.");
        return descriptions;
    }

    public static Map<Integer, String> generateDetails() {
        Map<Integer, String> details =
                new LinkedHashMap<Integer, String>();
        details.put(1, "White leather; Gold-toned hardware; Horsebit; Inside open pocket; Detachable Chain Shoulder Strain: 21 cm;  Detachable Web shoulder strap: 40cm; Approximate Weight: 600g; Dimensions: W26cm x H16cm x D4cm; Made in Italy");
        details.put(2, "Beige and ebony GG Supreme canvas; Brown leather trim; Gold-toned hardware; Leather lining; Round Interlocking G; Shoulder strap with 39cm drop; Dimensions: W28cm x H16cm x D4cm; Made in Italy");
        details.put(3, "Embossed Monogram Empreinte cowhide leather; Cotton and Polyester lining; Cowhide-leather trim; Gold-color hardware; Zipped Closure; Dimensions: 19 x 11.5 x 3cm");
        details.put(4, "Monogram Empreinte embossed supple grained cowhide leather; Supple grained cowhide-leather trim; Textile lining; Gold-color hardware; Press-stud closure; Dimensions: 21 x 12 x 3 cm");
        details.put(5, "Embossed Monogram Empreinte cowhide leather; Cotton and Polyester lining; Cowhide-leather trim; Gold-color hardware; Zipped Closure; Dimensions: 19 x 11.5 x 3cm");
        details.put(6, "Signature jacquard and smooth leather; Eight credit card slots; Inside zip and open pockets; Snap closure, fabric lining; Outside open pocket; Dimansions 20 x 13 x 3.5 cm");
        details.put(7, "'CD' signature on the front; Flap closure; 2 compartments; 1 zipped pocket; 3 card slots; 1 rear curved patch pocket; Removable chain and leather shoulder strap; Made in Italy; Dimensions: 19.5 x 11 x 6.5 cm");
        details.put(8, "Height: 18 cm; Depth: 9.5 cm; Width: 26 cm; Weight: 0.6 kg; Shoulder strap drop: 43 cm");
        details.put(9, "72% Viscose, 28% Silk; Dimensions: 3 x 19 x 11 cm; Gold-toned metal hardware; Magnetic snap closure; Leather lining; Made in Italy");
        details.put(10, "90% Calfskin, 10% Brass; Dimensions: 21 x 16 x 3 cm; Gold-toned metal hardware; Grosgrain lining; Made in Italy");
        return details;
    }

    public static Map<Integer, String> generateProductCare() {
        Map<Integer, String> productCare =
                new LinkedHashMap<Integer, String>();
        productCare.put(1, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCare.put(2, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCare.put(3, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCare.put(4, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(5, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(6, "To remove dirt or dust, use a cloth or a sponge dampened with water.");
        productCare.put(7, "To remove dirt or dust, use a cloth or a sponge dampened with water.");
        productCare.put(8, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(9, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(10, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        return productCare;
    }

    public static Map<Integer, Double> generatePrice() {
        Map<Integer, Double> prices =
                new LinkedHashMap<Integer, Double>();
        prices.put(1, 4195.00);
        prices.put(2, 4290.00);
        prices.put(3, 2550.00);
        prices.put(4, 2630.00);
        prices.put(5, 2630.00);
        prices.put(6, 550.00);
        prices.put(7, 6094.00);
        prices.put(8, 4970.00);
        prices.put(9, 2550.00);
        prices.put(10, 1154.00);
        return prices;
    }

    public static Map<Integer, String> generateColourType() {
        Map<Integer, String> colourTypes =
                new LinkedHashMap<Integer, String>();
        colourTypes.put(1, "White");
        colourTypes.put(2, "Brown");
        colourTypes.put(3, "Black");
        colourTypes.put(4, "Black");
        colourTypes.put(5, "White");
        colourTypes.put(6, "White");
        colourTypes.put(7, "Blue");
        colourTypes.put(8, "Purple");
        colourTypes.put(9, "Black");
        colourTypes.put(10, "Pink");
        return colourTypes;
    }
}
