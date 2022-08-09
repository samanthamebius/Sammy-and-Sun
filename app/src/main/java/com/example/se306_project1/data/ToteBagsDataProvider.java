package com.example.se306_project1.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.se306_project1.models.IProduct;
import com.example.se306_project1.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ToteBagsDataProvider {

    // Add number documents to Firestore
    public static void addToteBagstoFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<IProduct> TotesList = getToteBags();
        for (IProduct aTote : TotesList) {
            db.collection("tote bags").document("tote" + aTote.getProductID()).set(aTote).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("Tote Bags Collection Add", "tote" + aTote.getBrandName() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.w("Tote Bags Collection Add", "tote" + aTote.getBrandName() + " NOT added.");
                }
            });
        }
    }

    public static List<IProduct> getToteBags() {
        List<IProduct>bagsList = new ArrayList<>();

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
            int categoryID = 3;

            Double productPrice = prices.get(key);
            String productLongName = longNames.get(key);
            String productShortName= shortNames.get(key);

            ArrayList<String> productImages = new ArrayList<>();
            productImages.add("tote"+key+"_01.png");
            productImages.add("tote"+key+"_02.png");
            productImages.add("tote"+key+"_03.png");

            String productDescription = descriptions.get(key);
            String productDetails = details.get(key);
            String productCare = productCares.get(key);
            String brandName = brands.get(key);
            String productColourType = colourTypes.get(key);


            Product cb = new Product(productID, categoryID, productPrice, productLongName, productShortName, productImages, productDescription, productDetails, productCare, brandName, productColourType);
            bagsList.add(cb);
        }

        return bagsList;
    }

    public static Map<Integer, String> generateBrand() {
        Map<Integer, String> brands =
                new LinkedHashMap<Integer, String>();
        brands.put(1, "Gucci");
        brands.put(2, "Gucci");
        brands.put(3, "Prada");
        brands.put(4, "Prada");
        brands.put(5, "Dior");
        brands.put(6, "Louis Vuitton");
        brands.put(7, "Fendi");
        brands.put(8, "Saint Laurant");
        brands.put(9, "Balenciaga");
        brands.put(10, "Balenciaga");
        return brands;
    }

    public static Map<Integer, String> generateLongName() {
        Map<Integer, String> longNames =
                new LinkedHashMap<Integer, String>();
        longNames.put(1, "Ophidia GG Medium Tote");
        longNames.put(2, "Diana Medium Tote Bag");
        longNames.put(3, "Small Saffiano Leather Double Prada Bag");
        longNames.put(4, "Re-Nylon Tote Bag");
        longNames.put(5, "Medium Book Tote");
        longNames.put(6, "Lockme Shopper");
        longNames.put(7, "Sunshine Medium");
        longNames.put(8, "Shopping E/W in Supple Leather");
        longNames.put(9, "Signature Large East-West Shopper Bag");
        longNames.put(10, "Tool 2.0 Small North-South Tote bag");
        return longNames;
    }

    public static Map<Integer, String> generateShortName() {
        Map<Integer, String> shortNames =
                new LinkedHashMap<Integer, String>();
        shortNames.put(1, "Ophidia");
        shortNames.put(2, "Diana");
        shortNames.put(3, "Saffiano");
        shortNames.put(4, "Re-Nylon");
        shortNames.put(5, "Medium Book");
        shortNames.put(6, "Lockme Shopper");
        shortNames.put(7, "Sunshine Medium");
        shortNames.put(8, "Shopping E/W ");
        shortNames.put(9, "Signature Large");
        shortNames.put(10, "Tool 2.0");
        return shortNames;
    }

    public static Map<Integer, String> generateDescription() {
        Map<Integer, String> descriptions =
                new LinkedHashMap<Integer, String>();
        descriptions.put(1, "A cult fabric is imagined through the Gucci lens with the emblematic monogram motif. A part of the Ouverture collection, the Ophidia is presented in organic GG jacquard denim. The vintage inspired style is completed by the Double G - an archival symbol from the '70s.");
        descriptions.put(2, "First used in the post-war era when traditional materials were hard to come by, bamboo has become not only a symbol of the spirit of reinvention that runs throughout the House's designs but one of Gucci's most recognisable signatures. The Gucci Diana embodies this idea, representing the notion of style in constant evolution with its removable leather belts—a nod to the functional bands used to keep the handles in shape. Here, the bag is presented in black leather.");
        descriptions.put(3, "The Prada Double bag in Saffiano Cuir leather comes with a double handle and detachable shoulder strap. A flap pocket inside and lettering logo distinguish the bag with a nappa leather lining.");
        descriptions.put(4, "A roomy front pocket stands out on this tote bag with a classic design, accented with Saffiano leather handles and decorated with the distinctive triangle logo. Introduced to the luxury world precisely by Prada, nylon exemplifies the brand's propensity to combine concepts, workmanship and shapes that were previously considered irreconcilable. Here, the emblematic fabric is presented in its evolved, sustainable version produced from recycled, purified plastic material collected in the ocean.");
        descriptions.put(5, "Introduced by Maria Grazia Chiuri, Creative Director of Christian Dior, the Dior Book Tote has become a staple of the Dior aesthetic. Designed to hold all the daily essentials, the style is fully embroidered with a pink Toile de Jouy motif. Adorned with the 'CHRISTIAN DIOR PARIS' signature on the front, the medium tote exemplifies the House's signature savoir-faire and may be carried by hand or worn over the shoulder.");
        descriptions.put(6, "Comfortable to carry thanks to its two long leather straps, the Lockme Shopper, is instantly recognizable by its iconic LV turn lock, which secures the closure. Rounded and folded gussets give the bag a feminine feel and make it roomy enough to hold city-to-office essentials, including A4 files.");
        descriptions.put(7, "Medium Sunshine Shopper bag made from lilac leather with hot-stamped \"FENDI ROMA\" and stiff tortoiseshell plexiglass handles. Features a spacious lined internal compartment, edges in tone on tone leather, and gold-finish metalware. Can be carried by hand or worn on the shoulder thanks to the two handles and detachable shoulder strap. Made in Italy");
        descriptions.put(8, "Saint Laurent unstructured tote bag with flat leather handles and removable leather encased metal interlocking YSL signature charm.");
        descriptions.put(9, "Signature Large East-West Shopper Bag in beige and brown BB Monogram coated canvas, aged-gold hardware.");
        descriptions.put(10, "Tool 2.0 Small North-South Tote in off-white grained calfskin, aged silver hardware.");
        return descriptions;
    }

    public static Map<Integer, String> generateDetails() {
        Map<Integer, String> details =
                new LinkedHashMap<Integer, String>();
        details.put(1, "Dark blue and ivory eco washed organic GG jacquard denim; Brown leather trim; Gold-toned hardware; Cotton linen lining; Double G; Interior zipper pocket; Double leather handles with 25cm drop and 54cm length; Magnetic snap closure");
        details.put(2, "Black leather; Shiny antique gold-toned hardware; Microfibre lining with a suede-like finish; Double G; Interior open pocket and two zip compartments; Handle drop: 10cm; Strap drop: 58cm; Weight: 1.256kg approximately; Made in Italy");
        details.put(3, "Leather handles; Detachable and adjustable 115 cm leather shoulder strap; Leather name tag with metal details; Metal hardware; Metal lettering logo on a leather triangle at the front; Snap closure on the sides; Nappa leather lining with flap pocket; Small feet on the bottom; Height: 23cm; Length: 14cm; Width: 31cm");
        details.put(4, "Saffiano leather handles; Pocket with magnet closure; Metal hardware; Enameled metal triangle logo on the front; Zipper closure; Re-Nylon logo-print lining with two pockets, including one with zipper; Height: 29cm; Length: 15.5cm; Width: 35cm");
        details.put(5, "'CHRISTIAN DIOR PARIS' signature on the front; Dust bag included; Dimensions: 36 x 27.5 x 16.5 cm; Made in Italy");
        details.put(6, "Chataigne Brown; Grained calf leather; Grained calf-leather trim; Microfiber lining; Gold-color hardware; Dimensions: 38 x 26.5 x 13 cm");
        details.put(7, "Height: 31 cm; Depth: 17 cm; Width: 35 cm; Weight: 1.2 kg; Strap length (min): 90 cm; Strap length (max): 90 cm; Shoulder strap drop: 42 cm");
        details.put(8, "96.3% calfskin leather, 3.7% polyurandhane; Dimensions: 37 x 28 x 13 cm; Handle drop : 23,5 CM; Gold-toned hardware; Magnetic snap closure; Unlined; Made in Italy");
        details.put(9, "BB Monogram coated canvas; East-west shopper bag; Two top handles; Balenciaga logo printed at front; Aged-gold hardware; Open top; 1 inner zipped pocket; 1 inner patch pocket; Removable reinforced base; Cotton canvas lining; Made in Italy");
        details.put(10, "Grained calfskin; North-south tote bag; One top handle with two side hooks; Adjustable and removable crossbody strap; Foldable top part; Front Balenciaga logo; Reinforced bottom panel with brass feet; Inner detatchable zipped pouch; Made in Italy");
        return details;
    }

    public static Map<Integer, String> generateProductCare() {
        Map<Integer, String> productCares =
                new LinkedHashMap<Integer, String>();
        productCares.put(1, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(2, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(3, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(4, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(5, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        productCares.put(6, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCares.put(7, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCares.put(8, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        productCares.put(9, "Wipe with a soft cloth");
        productCares.put(10, "Wipe with a soft cloth");
        return productCares;
    }

    public static Map<Integer, Double> generatePrice() {
        Map<Integer, Double> prices =
                new LinkedHashMap<Integer, Double>();
        prices.put(1, 2565.00);
        prices.put(2, 6830.00);
        prices.put(3, 6100.00);
        prices.put(4, 2650.00);
        prices.put(5, 5367.00);
        prices.put(6, 5174.00);
        prices.put(7, 6328.00);
        prices.put(8, 2010.00);
        prices.put(9, 1790.00);
        prices.put(10, 2590.00);
        return prices;
    }

    public static Map<Integer, String> generateColourType() {
        Map<Integer, String> colourTypes =
                new LinkedHashMap<Integer, String>();
        colourTypes.put(1, "Blue");
        colourTypes.put(2, "Black");
        colourTypes.put(3, "White");
        colourTypes.put(4, "Brown");
        colourTypes.put(5, "Pink");
        colourTypes.put(6, "Brown");
        colourTypes.put(7, "Purple");
        colourTypes.put(8, "Black");
        colourTypes.put(9, "Brown");
        colourTypes.put(10, "White");
        return colourTypes;
    }
}
