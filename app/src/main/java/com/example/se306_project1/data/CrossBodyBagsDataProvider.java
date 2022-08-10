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

public class CrossBodyBagsDataProvider {

    // Add number documents to Firestore
    public static void addCrossBodyBagstoFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<IProduct> crossBodyBagsList = getCrossBodyBags();
        for (IProduct aCrossBody : crossBodyBagsList) {
            db.collection("cross body bags").document("" + aCrossBody.getProductID()).set(aCrossBody).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("Cross Body Bags Collection Add", "crossbody" + aCrossBody.getBrandName() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.w("Cross Body Bags Collection Add", "crossbody" + aCrossBody.getBrandName() + " NOT added.");
                }
            });
        }
    }

    public static List<IProduct> getCrossBodyBags() {
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
            int categoryID = 2;

            Double productPrice = prices.get(key);
            String productLongName = longNames.get(key);
            String productShortName= shortNames.get(key);

            ArrayList<String> productImages = new ArrayList<>();
            productImages.add("crossbody"+key+"_01.png");
            productImages.add("crossbody"+key+"_02.png");
            productImages.add("crossbody"+key+"_03.png");

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
        brands.put(20, "Gucci");
        brands.put(21, "Gucci");
        brands.put(22, "Prada");
        brands.put(23, "Prada");
        brands.put(24, "Louis Vuitton");
        brands.put(25, "Dior");
        brands.put(26, "Fendi");
        brands.put(27, "Louis Vuitton");
        brands.put(28, "Balenciaga");
        brands.put(29, "Saint Laurant");
        return brands;
    }

    public static Map<Integer, String> generateLongName() {
        Map<Integer, String> longNames =
                new LinkedHashMap<Integer, String>();
        longNames.put(20, "Blondie Medium Bag");
        longNames.put(21, "GG Marmont Matelasse Shoulder Bag");
        longNames.put(22, "Embleme Brushed-Leather Bag");
        longNames.put(23, "Cahier Leather Bag");
        longNames.put(24, "New Wave Chain Bag");
        longNames.put(25, "30 Montaigne Bag");
        longNames.put(26, "Mini Baguette");
        longNames.put(27, "Over The Moon");
        longNames.put(28, "Le Cagole XS Shoulder Bag Crocodile Embossed");
        longNames.put(29, "College Large Chain Bag in Quilted Leather");
        return longNames;
    }

    public static Map<Integer, String> generateShortName() {
        Map<Integer, String> shortNames =
                new LinkedHashMap<Integer, String>();
        shortNames.put(20, "Blondie");
        shortNames.put(21, "Marmont Matelasse");
        shortNames.put(22, "Embleme Brushed-Leather");
        shortNames.put(23, "Cahier");
        shortNames.put(24, "Wave Chain");
        shortNames.put(25, "30 Montaigne");
        shortNames.put(26, "Mini Baguette");
        shortNames.put(27, "Over The Moon");
        shortNames.put(28, "Le Cagole");
        shortNames.put(29, "College");
        return shortNames;
    }

    public static Map<Integer, String> generateDescription() {
        Map<Integer, String> descriptions =
                new LinkedHashMap<Integer, String>();
        descriptions.put(20, "Coming from the House's archives, a geometrically shaped version of the Interlocking G logo is reintroduced for Gucci Love Parade. The emblem enriches the design with a timeless feel - mirroring the Hollywood glamour that shaped the fashion show. This medium-sized version of the accessory is presented in brown leather.");
        descriptions.put(21, "The GG Marmont is reimagined through eye-catching new shades and textures. A recognisable member of the Beloved lines, the line is defined by its Double G hardware and matelassé leather. As a part of Gucci Love Parade, a new iteration of the line is introduced in blue with two different quilted geometric motifs. The small shoulder bag is completed by silver-toned Double G hardware.");
        descriptions.put(22, "Presented in a macro version for a bold, contemporary effect, the enameled metal triangle logo stands out on the flap of this shoulder bag made of brushed leather, an iconic and luxurious material of the brand. Modern elegance and versatility meet in the rectangular design, completed by an adjustable leather shoulder strap to be worn in different ways.");
        descriptions.put(23, "Inspired by the allure of precious and ancient volumes, the Prada Cahier bag is part of Prada's iconic lines and is presented here in a sophisticated monochrome version with a new color story composed of neutral shades. Featuring antiqued gold-tone hardware that recalls the bindings on books from past eras, the accessory has an adjustable leather shoulder strap so it can be worn in different ways.");
        descriptions.put(24, "The New Wave Chain Bag MM bag is made from puffy calf leather, with V and wave patterns quilted into the calfskin. The gold-color chain slides through engraved eyelets to allow short-shoulder carry or sporty cross-body wear while folded gussets make it supple and body-friendly. The interior keeps daily essentials organized.");
        descriptions.put(25, "The 30 Montaigne line, inspired by the hallmark address, offers essential pieces that embody the House's iconic codes. The handbag is crafted in beige box calfskin for an elegant and modern design. The flap has a tonal enameled metal 'CD' clasp, inspired by the seal of a Christian Dior perfume bottle. Other details, like the embossed '30 MONTAIGNE' signature at the back highlight the streamlined design. The style has an adjustable leather shoulder strap and can be carried by hand, worn over the shoulder or crossbody.");
        descriptions.put(26, "Iconic mini Baguette bag made of brown jacquard fabric with an FF motif, decorated with an FF clasp and trimmed with black embroidery. Featuring a front flap, magnetic clasp, unlined internal compartment with pocket and gold-finish metalware. The bag can be carried by hand, or worn either on the shoulder or cross-body thanks to the handle and chain shoulder strap, both detachable. Made in Italy");
        descriptions.put(27, "The Over The Moon delights with its versatile shape in quilted calf leather. The embroidered puffy Monogram pattern brings a statutory feel while the adjustable strap and gold-color chain deliver a myriad of carry options: elbow, short shoulder, cross-body, and long shoulder. With the strap removed, it can even be carried as an evening clutch.");
        descriptions.put(28, "Le Cagole XS Shoulder Bag in light purple supple crocodile embossed calfskin, aged silver hardware.");
        descriptions.put(29, "Saint Laurent monogram bag decorated wth chevron-quilted overstitching and metal YSL initials, featuring a top handle and removable chain and leather shoulder strap.");
        return descriptions;
    }

    public static Map<Integer, String> generateDetails() {
        Map<Integer, String> details =
                new LinkedHashMap<Integer, String>();
        details.put(20, "Brown leather; Gold-toned hardware; Cotton linen lining; Round Interlocking G; Weight: 1180g, approximately; Medium size: W29cm x H22cm x D7cm; Made in Italy");
        details.put(21, "Blue matelassé leather; Palladium-toned hardware; Microfibre lining with a suede-like finish; Double G; Interior Open Pocket; Zip top closure; Dimensions: 24 x 13 x 7 cm; Made in Italy ");
        details.put(22, "Adjustable leather handle; Metal hardware; Enameled metal triangle logo; Magnetic flap closure; Nappa leather interior with zipper pocket and two compartments; Height: 14cm; Length: 5cm; Width: 24cm");
        details.put(23, "Detachable adjustable 105 cm leather shoulder strap; Metal hardware; Metal lettering logo on the strap; Flap closure with strap and metal loop; Leather interior with two pockets, one with flap; Height: 14.5cm; Length: 7cm; Width: 20cm");
        details.put(24, "Rose Ballerine Pink; Quilted cowhide leather; Smooth cowhide leather trim; Microfiber lining; Aged-gold-color hardware; Magnetic LV Lock; Dimensions: 24 x 14 x 9 cm");
        details.put(25, "Flap design closure; 'CD' clasp; Embossed '30 MONTAIGNE' signature on the back; Adjustable leather shoulder strap with 'Christian Dior' military-inspired buckle; Interior zip pocket and phone pocket; Back pocket; Dust bag included; Made in Italy");
        details.put(26, "Height: 11.5 cm; Depth: 4 cm; Width: 19 cm; Weight: 0.6 kg; Shoulder strap drop: 58 cm");
        details.put(27, "Quilted and embroidered smooth calf leather; Nylon strap; Jacquard with Nano Monogram lining; Gold-color hardware; 1 adjustable & removable nylon strap for shoulder or crossbody carry; 1 removable chain");
        details.put(28, "Dimensions : L10,2 x H6,3 x W2,7 inch; Supple crocodile embossed calfskin; Shoulder bag; Leather braided shoulder pad; Zipped closure with knotted leather puller; Aged silver hardware; Made in Italy");
        details.put(29, "100% lambskin; Dimensions 32 x 20 x 8.5 cm; Magnetic snap closure; Grosgrain lining; Made in Italy");
        return details;
    }

    public static Map<Integer, String> generateProductCare() {
        Map<Integer, String> productCares =
                new LinkedHashMap<Integer, String>();
        productCares.put(20, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(21, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(22, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(23, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth; Fill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag; Do not carry heavy products that may affect the shape of the bag; Clean with a soft, dry cloth");
        productCares.put(24, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCares.put(25, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        productCares.put(26, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim; Keep your product away from damp or humid environments and avoid direct exposure to sunlight; Avoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCares.put(27, "Keep your product away from water. Should it get wet or dirty on the surface, dry with a lint free, light-colored, absorbent cloth. Never use soap or solvent. In order to protect your product when you are not using it, store it in the felt protective pouch provided");
        productCares.put(28, "Wipe with a soft cloth");
        productCares.put(29, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        return productCares;
    }

    public static Map<Integer, Double> generatePrice() {
        Map<Integer, Double> prices =
                new LinkedHashMap<Integer, Double>();
        prices.put(20, 5675.00);
        prices.put(21, 2875.00);
        prices.put(22, 5800.00);
        prices.put(23, 5600.00);
        prices.put(24, 4075.00);
        prices.put(25, 5674.00);
        prices.put(26, 3820.00);
        prices.put(27, 4762.00);
        prices.put(28, 3500.00);
        prices.put(29, 4780.00);
        return prices;
    }

    public static Map<Integer, String> generateColourType() {
        Map<Integer, String> colourTypes =
                new LinkedHashMap<Integer, String>();
        colourTypes.put(20, "Brown");
        colourTypes.put(21, "Blue");
        colourTypes.put(22, "Black");
        colourTypes.put(23, "White");
        colourTypes.put(24, "Pink");
        colourTypes.put(25, "White");
        colourTypes.put(26, "Brown");
        colourTypes.put(27, "Black");
        colourTypes.put(28, "Purple");
        colourTypes.put(29, "White");
        return colourTypes;
    }
}
