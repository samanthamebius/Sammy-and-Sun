package com.example.se306_project1.data;

import android.graphics.Color;
import android.util.Log;

import com.example.se306_project1.models.Brand;
import com.example.se306_project1.models.ColourType;
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

public class ProductsDataProvider {

    // Add number documents to Firestore
    public static void addProductsToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<IProduct> productsList = getProducts();
        for (IProduct aProduct : productsList) {
            db.collection("products").document("" + aProduct.getProductID()).set(aProduct).addOnSuccessListener(new OnSuccessListener<Void>() {

                @Override
                public void onSuccess(Void unused) {
                    Log.d("Products Collection Add", "product " + aProduct.getProductID() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.w("Products Collection Add", "product " + aProduct.getProductID() + " NOT added.");
                }
            });

        }

        for (IProduct aProduct : productsList) {
            db.collection("category"+aProduct.getCategoryID()).document("" + aProduct.getProductID()).set(aProduct).addOnSuccessListener(new OnSuccessListener<Void>() {

                @Override
                public void onSuccess(Void unused) {
                    Log.d("Products Collection Add", "product " + aProduct.getProductID() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.w("Products Collection Add", "product " + aProduct.getProductID() + " NOT added.");
                }
            });

        }
    }

    public static List<IProduct> getProducts() {
        List<IProduct> bagsList = new ArrayList<>();

        Map<Integer, Integer> categories = generateCategory();
        Map<Integer, String> brands = generateBrand();
        Map<Integer, String> longNames = generateLongName();
        Map<Integer, String> shortNames = generateShortName();
        Map<Integer, String> descriptions = generateDescription();
        Map<Integer, String> details = generateDetails();
        Map<Integer, String> productCares = generateProductCare();
        Map<Integer, Double> prices = generatePrice();
        Map<Integer, String> colourTypes = generateColourType();

        for (Integer key : brands.keySet()) {

            long productID = key;
            long categoryID = categories.get(key);

            Double productPrice = prices.get(key);
            String productLongName = longNames.get(key);
            String productShortName= shortNames.get(key);

            long productCountVisit = 0;
            boolean isFavourite = false;

            ArrayList<String> productImages = new ArrayList<>();
            productImages.add("b"+key+"_01");
            productImages.add("b"+key+"_02");
            productImages.add("b"+key+"_03");

            String productDescription = descriptions.get(key);
            String productDetails = details.get(key);
            String productCare = productCares.get(key);
            Brand brandName = Brand.valueOf(brands.get(key));
            ColourType productColourType = ColourType.valueOf(colourTypes.get(key)) ;


            Product c = new Product(productID, categoryID, productPrice, productLongName, productShortName, brandName, productDescription, productDetails, productCare, productColourType, productCountVisit, isFavourite,productImages);
            bagsList.add(c);
        }

        return bagsList;
    }

    public static Map<Integer, Integer> generateCategory() {
        Map<Integer, Integer> categories =
                new LinkedHashMap<Integer, Integer>();
        categories.put(0, 0);
        categories.put(1, 0);
        categories.put(2, 0);
        categories.put(3, 0);
        categories.put(4, 0);
        categories.put(5, 0);
        categories.put(6, 0);
        categories.put(7, 0);
        categories.put(8, 0);
        categories.put(9, 0);
        categories.put(10, 1);
        categories.put(11, 1);
        categories.put(12, 1);
        categories.put(13, 1);
        categories.put(14, 1);
        categories.put(15, 1);
        categories.put(16, 1);
        categories.put(17, 1);
        categories.put(18, 1);
        categories.put(19, 1);
        categories.put(20, 2);
        categories.put(21, 2);
        categories.put(22, 2);
        categories.put(23, 2);
        categories.put(24, 2);
        categories.put(25, 2);
        categories.put(26, 2);
        categories.put(27, 2);
        categories.put(28, 2);
        categories.put(29, 2);
        return categories;
    }

    public static Map<Integer, String> generateBrand() {
        Map<Integer, String> brands =
                new LinkedHashMap<Integer, String>();
        brands.put(0, Brand.Gucci.toString());
        brands.put(1, Brand.Gucci.toString());
        brands.put(2, Brand.Prada.toString());
        brands.put(3, Brand.Louis_Vuitton.toString());
        brands.put(4, Brand.Louis_Vuitton.toString());
        brands.put(5, Brand.Coach.toString());
        brands.put(6, Brand.Dior.toString());
        brands.put(7, Brand.Fendi.toString());
        brands.put(8,  Brand.Saint_Laurent.toString());
        brands.put(9, Brand.Saint_Laurent.toString());
        brands.put(10, Brand.Gucci.toString());
        brands.put(11, Brand.Gucci.toString());
        brands.put(12, Brand.Prada.toString());
        brands.put(13, Brand.Prada.toString());
        brands.put(14, Brand.Dior.toString());
        brands.put(15, Brand.Louis_Vuitton.toString());
        brands.put(16,  Brand.Fendi.toString());
        brands.put(17, Brand.Saint_Laurent.toString());
        brands.put(18, Brand.Balenciaga.toString());
        brands.put(19, Brand.Balenciaga.toString());
        brands.put(20, Brand.Gucci.toString());
        brands.put(21, Brand.Gucci.toString());
        brands.put(22, Brand.Prada.toString());
        brands.put(23, Brand.Prada.toString());
        brands.put(24, Brand.Louis_Vuitton.toString());
        brands.put(25, Brand.Dior.toString());
        brands.put(26,  Brand.Fendi.toString());
        brands.put(27, Brand.Louis_Vuitton.toString());
        brands.put(28, Brand.Balenciaga.toString());
        brands.put(29, Brand.Saint_Laurent.toString());
        return brands;
    }

    public static Map<Integer, String> generateLongName() {
        Map<Integer, String> longNames =
                new LinkedHashMap<Integer, String>();
        longNames.put(0, "Horsebit 1955 Small Bag");
        longNames.put(1, "Blondie Shoulder Bag");
        longNames.put(2, "Leather Triangle Pouch");
        longNames.put(3, "Felicie Pochette");
        longNames.put(4, "Easy Pouch On Strap");
        longNames.put(5, "Tabby Chain Clutch In Signature Jacquard");
        longNames.put(6, "Caro Macrocannage Pouch");
        longNames.put(7, "First Small Leather Bag");
        longNames.put(8, "Sade Large Clutch in Satin");
        longNames.put(9, "Cassandre Matelasse Flap Pouch");
        longNames.put(10, "Ophidia GG Medium Tote");
        longNames.put(11, "Diana Medium Tote Bag");
        longNames.put(12, "Small Saffiano Leather Double Prada Bag");
        longNames.put(13, "Re-Nylon Tote Bag");
        longNames.put(14, "Medium Book Tote");
        longNames.put(15, "Lockme Shopper");
        longNames.put(16, "Sunshine Medium");
        longNames.put(17, "Shopping E/W in Supple Leather");
        longNames.put(18, "Signature Large East-West Shopper Bag");
        longNames.put(19, "Tool 2.0 Small North-South Tote bag");
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
        shortNames.put(0, "Horsebit 1955");
        shortNames.put(1, "Blondie Shoulder");
        shortNames.put(2, "Triangle Pouch");
        shortNames.put(3, "Felicie Pochette");
        shortNames.put(4, "Easy Pouch");
        shortNames.put(5, "Tabby");
        shortNames.put(6, "Caro Macrocannage");
        shortNames.put(7, "First");
        shortNames.put(8, "Sade");
        shortNames.put(9, "Cassandre Matelasse");
        shortNames.put(10, "Ophidia");
        shortNames.put(11, "Diana");
        shortNames.put(12, "Saffiano");
        shortNames.put(13, "Re-Nylon");
        shortNames.put(14, "Medium Book");
        shortNames.put(15, "Lockme Shopper");
        shortNames.put(16, "Sunshine Medium");
        shortNames.put(17, "Shopping E/W ");
        shortNames.put(18, "Signature Large");
        shortNames.put(19, "Tool 2.0");
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
        descriptions.put(0, "Embodying the notion of the versatility, the Gucci Horsebit 1955 is enriched with two detachable shoulder straps, one is crafted in emblematic green and red Web the other is in Gucci’s recognisable gold-toned chain design. The style is crafted from white leather and defined by the line's signature Horsebit hardware.");
        descriptions.put(1, "Vintage elements are paired with archival details as an ode to the glamour that permeated the Gucci Love Parade fashion show. This shoulder bag pairs a delicate chain strap with GG Supreme canvas to infuse the accessory with a timeless feel. Reintroduced in honour of the collection, the rounded silhouette is completed by a historical, rounded iteration of Guccio Gucci's monogram.");
        descriptions.put(2, "A study of the triangle inspires new geometric elements and novel interpretations of Prada's historic stylistic code. The iconic shape is presented again in this sleek pouch that comes with a strap for versatility. The tonal embossed logo and the enameled metal triangle logo enhance the minimalist aesthetics.");
        descriptions.put(3, "The Pochette Felicie is crafted from Monogram Empreinte leather, embossed with Louis Vuitton's signature Monogram pattern. Designed to adapt to modern lifestyles, this envelope-style pouch contains a spacious compartment with two removable pockets: one zipped, one with card slots. It can also be carried in different ways thanks to a removable gold-tone chain.");
        descriptions.put(4, "For Spring 2022, the Easy Pouch On Strap is made from Monogram Empreinte leather. The sleek design lends itself beautifully to the embossed Monogram pattern on the supple Empreinte leather. It can be worn on the shoulder, with the chain or the strap for a short carry, or cross-body as the leather strap is adjustable.");
        descriptions.put(5, "A modern take on an archival 1970s Coach design, our structured Tabby clutch is crafted of our environmentally conscious Signature jacquard made from a blend of organic cotton and recycled plastic bottles--all part of our commitment to rethinking and reducing our impact on the planet by repurposing waste materials. Featuring our Signature hardware, this organized, three compartment design has a secure interior zippered compartment and an exterior slip pocket for easy access to essentials.");
        descriptions.put(6, "This season's new Dior Caro pouch showcases the House's couture spirit with a practical design. Crafted in azure blue calfskin, it stands out with a unique Macrocannage quilted effect. The flap has an antique gold-finish metal 'CD' signature, and reveals compartments to hold all the essentials.");
        descriptions.put(7, "Small Fendi First bag made of soft, lilac nappa leather with oversized metal F clasp bound in tone on tone nappa leather. Featuring an interior compartment lined in fabric with the iconic FF motif, removable inner hooks and gold-finish metalware. Can be carried by hand as a clutch or worn on the shoulder thanks to the detachable shoulder strap. Made in Italy.");
        descriptions.put(8, "Puffer envelope clutch with magnetic front flap. Decorated with all-over quilted overstitching and metal YSL initials at the back.");
        descriptions.put(9, "Monogram envelpe cliutch with a front flap, made with metal free tanned leather and organic cotton lining and decorated with metal YSL initials and chevron quilted overstitching, featuring a detachable wristlit.");
        descriptions.put(10, "A cult fabric is imagined through the Gucci lens with the emblematic monogram motif. A part of the Ouverture collection, the Ophidia is presented in organic GG jacquard denim. The vintage inspired style is completed by the Double G - an archival symbol from the '70s.");
        descriptions.put(11, "First used in the post-war era when traditional materials were hard to come by, bamboo has become not only a symbol of the spirit of reinvention that runs throughout the House's designs but one of Gucci's most recognisable signatures. The Gucci Diana embodies this idea, representing the notion of style in constant evolution with its removable leather belts—a nod to the functional bands used to keep the handles in shape. Here, the bag is presented in black leather.");
        descriptions.put(12, "The Prada Double bag in Saffiano Cuir leather comes with a double handle and detachable shoulder strap. A flap pocket inside and lettering logo distinguish the bag with a nappa leather lining.");
        descriptions.put(13, "A roomy front pocket stands out on this tote bag with a classic design, accented with Saffiano leather handles and decorated with the distinctive triangle logo. Introduced to the luxury world precisely by Prada, nylon exemplifies the brand's propensity to combine concepts, workmanship and shapes that were previously considered irreconcilable. Here, the emblematic fabric is presented in its evolved, sustainable version produced from recycled, purified plastic material collected in the ocean.");
        descriptions.put(14, "Introduced by Maria Grazia Chiuri, Creative Director of Christian Dior, the Dior Book Tote has become a staple of the Dior aesthetic. Designed to hold all the daily essentials, the style is fully embroidered with a pink Toile de Jouy motif. Adorned with the 'CHRISTIAN DIOR PARIS' signature on the front, the medium tote exemplifies the House's signature savoir-faire and may be carried by hand or worn over the shoulder.");
        descriptions.put(15, "Comfortable to carry thanks to its two long leather straps, the Lockme Shopper, is instantly recognizable by its iconic LV turn lock, which secures the closure. Rounded and folded gussets give the bag a feminine feel and make it roomy enough to hold city-to-office essentials, including A4 files.");
        descriptions.put(16, "Medium Sunshine Shopper bag made from lilac leather with hot-stamped \"FENDI ROMA\" and stiff tortoiseshell plexiglass handles. Features a spacious lined internal compartment, edges in tone on tone leather, and gold-finish metalware. Can be carried by hand or worn on the shoulder thanks to the two handles and detachable shoulder strap. Made in Italy");
        descriptions.put(17, "Saint Laurent unstructured tote bag with flat leather handles and removable leather encased metal interlocking YSL signature charm.");
        descriptions.put(18, "Signature Large East-West Shopper Bag in beige and brown BB Monogram coated canvas, aged-gold hardware.");
        descriptions.put(19, "Tool 2.0 Small North-South Tote in off-white grained calfskin, aged silver hardware.");
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
        details.put(0, "White leather\nGold-toned hardware\nHorsebit\nInside open pocket\nDetachable Chain Shoulder Strain: 21 cm\nDetachable Web shoulder strap: 40cm\nApproximate Weight: 600g\nDimensions: W26cm x H16cm x D4 cm\nMade in Italy");
        details.put(1, "Beige and ebony GG Supreme canvas\nBrown leather trim\nGold-toned hardware\nLeather lining\nRound Interlocking G\nShoulder strap with 39cm drop\nDimensions: W28cm x H16cm x D4cm\nMade in Italy");
        details.put(2, "Embossed Monogram Empreinte cowhide leather\nCotton and Polyester lining\nCowhide-leather trim\nGold-color hardware\nZipped Closure\nDimensions: 19 x 11.5 x 3cm");
        details.put(3, "Monogram Empreinte embossed supple grained cowhide leather\nSupple grained cowhide-leather trim\nTextile lining\nGold-color hardware\nPress-stud closure\nDimensions: 21 x 12 x 3 cm");
        details.put(4, "Embossed Monogram Empreinte cowhide leather\nCotton and Polyester lining\nCowhide-leather trim\nGold-color hardware\nZipped Closure\nDimensions: 19 x 11.5 x 3cm");
        details.put(5, "Signature jacquard and smooth leather\nEight credit card slots\nInside zip and open pockets\nSnap closure, fabric lining\nOutside open pocket\nDimansions 20 x 13 x 3.5 cm");
        details.put(6, "'CD' signature on the front\nFlap closure\n2 compartments\n1 zipped pocket\n3 card slots\n1 rear curved patch pocket\nRemovable chain and leather shoulder strap\nMade in Italy\nDimensions: 19.5 x 11 x 6.5 cm");
        details.put(7, "Height: 18 cm\nDepth: 9.5 cm\nWidth: 26 cm\nWeight: 0.6 kg\nShoulder strap drop: 43 cm");
        details.put(8, "72% Viscose, 28% Silk\nDimensions: 3 x 19 x 11 cm\nGold-toned metal hardware\nMagnetic snap closure\nLeather lining\nMade in Italy");
        details.put(9, "90% Calfskin, 10% Brass\nDimensions: 21 x 16 x 3 cm\nGold-toned metal hardware\nGrosgrain lining\nMade in Italy");
        details.put(10, "Dark blue and ivory eco washed organic GG jacquard denim\nBrown leather trim\nGold-toned hardware\nCotton linen lining\nDouble G\nInterior zipper pocket\nDouble leather handles with 25cm drop and 54cm length\nMagnetic snap closure");
        details.put(11, "Black leather\nShiny antique gold-toned hardware\nMicrofibre lining with a suede-like finish\nDouble G\nInterior open pocket and two zip compartments\nHandle drop: 10cm\nStrap drop: 58cm\nWeight: 1.256kg approximately\nMade in Italy");
        details.put(12, "Leather handles\nDetachable and adjustable 115 cm leather shoulder strap\nLeather name tag with metal details\nMetal hardware\nMetal lettering logo on a leather triangle at the front\nSnap closure on the sides\nNappa leather lining with flap pocket\nSmall feet on the bottom\nHeight: 23cm\nLength: 14cm\nWidth: 31cm");
        details.put(13, "Saffiano leather handles\nPocket with magnet closure\nMetal hardware\nEnameled metal triangle logo on the front\nZipper closure\nRe-Nylon logo-print lining with two pockets, including one with zipper\nHeight: 29cm\nLength: 15.5cm\nWidth: 35cm");
        details.put(14, "'CHRISTIAN DIOR PARIS' signature on the front\nDust bag included\nDimensions: 36 x 27.5 x 16.5 cm\nMade in Italy");
        details.put(15, "Chataigne Brown\nGrained calf leather\nGrained calf-leather trim\nMicrofiber lining\nGold-color hardware\nDimensions: 38 x 26.5 x 13 cm");
        details.put(16, "Height: 31 cm\nDepth: 17 cm\nWidth: 35 cm\nWeight: 1.2 kg\nStrap length (min): 90 cm\nStrap length (max): 90 cm\nShoulder strap drop: 42 cm");
        details.put(17, "96.3% calfskin leather, 3.7% polyurandhane\nDimensions: 37 x 28 x 13 cm\nHandle drop : 23,5 CM\nGold-toned hardware\nMagnetic snap closure\nUnlined\nMade in Italy");
        details.put(18, "BB Monogram coated canvas\nEast-west shopper bag\nTwo top handles\nBalenciaga logo printed at front\nAged-gold hardware\nOpen top\n1 inner zipped pocket\n1 inner patch pocket\nRemovable reinforced base\nCotton canvas lining\nMade in Italy");
        details.put(19, "Grained calfskin\nNorth-south tote bag\nOne top handle with two side hooks\nAdjustable and removable crossbody strap\nFoldable top part\nFront Balenciaga logo\nReinforced bottom panel with brass feet\nInner detatchable zipped pouch\nMade in Italy");
        details.put(20, "Brown leather\nGold-toned hardware\nCotton linen lining\nRound Interlocking G\nWeight: 1180g, approximately\nMedium size: W29cm x H22cm x D7cm\nMade in Italy");
        details.put(21, "Blue matelassé leather\nPalladium-toned hardware\nMicrofibre lining with a suede-like finish\nDouble G\nInterior Open Pocket\nZip top closure\nDimensions: 24 x 13 x 7 cm\nMade in Italy ");
        details.put(22, "Adjustable leather handle\nMetal hardware\nEnameled metal triangle logo\nMagnetic flap closure\nNappa leather interior with zipper pocket and two compartments\nHeight: 14cm\nLength: 5cm\nWidth: 24cm");
        details.put(23, "Detachable adjustable 105 cm leather shoulder strap\nMetal hardware\nMetal lettering logo on the strap\nFlap closure with strap and metal loop\nLeather interior with two pockets, one with flap\nHeight: 14.5cm\nLength: 7cm\nWidth: 20cm");
        details.put(24, "Rose Ballerine Pink\nQuilted cowhide leather\nSmooth cowhide leather trim\nMicrofiber lining\nAged-gold-color hardware\nMagnetic LV Lock\nDimensions: 24 x 14 x 9 cm");
        details.put(25, "Flap design closure\n'CD' clasp\nEmbossed '30 MONTAIGNE' signature on the back\nAdjustable leather shoulder strap with 'Christian Dior' military-inspired buckle\nInterior zip pocket and phone pocket\nBack pocket\nDust bag included\nMade in Italy");
        details.put(26, "Height: 11.5 cm\nDepth: 4 cm\nWidth: 19 cm\nWeight: 0.6 kg\nShoulder strap drop: 58 cm");
        details.put(27, "Quilted and embroidered smooth calf leather\nNylon strap\nJacquard with Nano Monogram lining\nGold-color hardware\n1 adjustable & removable nylon strap for shoulder or crossbody carry\n1 removable chain");
        details.put(28, "Dimensions : L10,2 x H6,3 x W2,7 inch\nSupple crocodile embossed calfskin\nShoulder bag\nLeather braided shoulder pad\nZipped closure with knotted leather puller\nAged silver hardware\nMade in Italy");
        details.put(29, "100% lambskin\nDimensions 32 x 20 x 8.5 cm\nMagnetic snap closure\nGrosgrain lining\nMade in Italy");
        return details;
    }

    public static Map<Integer, String> generateProductCare() {
        Map<Integer, String> productCare =
                new LinkedHashMap<Integer, String>();
        productCare.put(0, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(1, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(2, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(3, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(4, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(5, "To remove dirt or dust, use a cloth or a sponge dampened with water.");
        productCare.put(6, "To remove dirt or dust, use a cloth or a sponge dampened with water.");
        productCare.put(7, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(8, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(9, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions.");
        productCare.put(10, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(11, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(12, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(13, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(14, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        productCare.put(15, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCare.put(16, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCare.put(17, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        productCare.put(18, "Wipe with a soft cloth");
        productCare.put(19, "Wipe with a soft cloth");
        productCare.put(20, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(21, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(22, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(23, "Protect from direct light, heat and rain. Should it become wet, dry it immediately with a soft cloth\nFill the bag with tissue paper to help maintain its shape and absorb humidity, and store in the provided flannel bag\nDo not carry heavy products that may affect the shape of the bag\nClean with a soft, dry cloth");
        productCare.put(24, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCare.put(25, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        productCare.put(26, "Beware not to scratch or rub your product against abrasive surfaces, especially the leather trim\nKeep your product away from damp or humid environments and avoid direct exposure to sunlight\nAvoid contact with greasy substances, cosmetics, perfume, and hydroalcoholic solutions");
        productCare.put(27, "Keep your product away from water. Should it get wet or dirty on the surface, dry with a lint free, light-colored, absorbent cloth. Never use soap or solvent. In order to protect your product when you are not using it, store it in the felt protective pouch provided");
        productCare.put(28, "Wipe with a soft cloth");
        productCare.put(29, "To remove dirt or dust, use a cloth or a sponge dampened with water");
        return productCare;
    }

    public static Map<Integer, Double> generatePrice() {
        Map<Integer, Double> prices =
                new LinkedHashMap<Integer, Double>();
        prices.put(0, 4195.00);
        prices.put(1, 4290.00);
        prices.put(2, 2550.00);
        prices.put(3, 2630.00);
        prices.put(4, 2630.00);
        prices.put(5, 550.00);
        prices.put(6, 6094.00);
        prices.put(7, 4970.00);
        prices.put(8, 2550.00);
        prices.put(9, 1154.00);
        prices.put(10, 2565.00);
        prices.put(11, 6830.00);
        prices.put(12, 6100.00);
        prices.put(13, 2650.00);
        prices.put(14, 5367.00);
        prices.put(15, 5174.00);
        prices.put(16, 6328.00);
        prices.put(17, 2010.00);
        prices.put(18, 1790.00);
        prices.put(19, 2590.00);
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
        colourTypes.put(0, ColourType.White.toString());
        colourTypes.put(1, ColourType.Black.toString());
        colourTypes.put(2, ColourType.Black.toString());
        colourTypes.put(3, ColourType.Black.toString());
        colourTypes.put(4, ColourType.White.toString());
        colourTypes.put(5, ColourType.White.toString());
        colourTypes.put(6, ColourType.Blue.toString());
        colourTypes.put(7, ColourType.Purple.toString());
        colourTypes.put(8, ColourType.Black.toString());
        colourTypes.put(9, ColourType.Pink.toString());
        colourTypes.put(10, ColourType.Blue.toString());
        colourTypes.put(11, ColourType.Black.toString());
        colourTypes.put(12, ColourType.White.toString());
        colourTypes.put(13, ColourType.Brown.toString());
        colourTypes.put(14, ColourType.Pink.toString());
        colourTypes.put(15, ColourType.Brown.toString());
        colourTypes.put(16, ColourType.Purple.toString());
        colourTypes.put(17, ColourType.Black.toString());
        colourTypes.put(18, ColourType.Brown.toString());
        colourTypes.put(19, ColourType.White.toString());
        colourTypes.put(20, ColourType.Brown.toString());
        colourTypes.put(21, ColourType.Blue.toString());
        colourTypes.put(22, ColourType.Black.toString());
        colourTypes.put(23, ColourType.White.toString());
        colourTypes.put(24, ColourType.Pink.toString());
        colourTypes.put(25, ColourType.White.toString());
        colourTypes.put(26, ColourType.Brown.toString());
        colourTypes.put(27, ColourType.Black.toString());
        colourTypes.put(28, ColourType.Purple.toString());
        colourTypes.put(29, ColourType.White.toString());
        return colourTypes;
    }
}
