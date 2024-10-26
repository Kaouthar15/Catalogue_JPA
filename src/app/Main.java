package app;

import services.CategoryService;
import services.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
//        déclaration d'une entity manager factory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogue");
        EntityManager em = emf.createEntityManager();

//      instanciation des répertoires
        CategoryService categoryService = new CategoryService(em);
        ProductService productService = new ProductService(em);

//      commencement du trasaction
        em.getTransaction().begin();

//        création d'une categorie
        entities.Category category = new entities.Category();
        category.setNom("Electronique");
        categoryService.save(category).ifPresent(cat -> System.out.println("Categorie enregistré: " + cat.getNom()));

//        instaciation d'un nouveau produit
        entities.Product produit = new entities.Product();
        produit.setDesignation("Smartphone");
        produit.setPrix(799.99);
        produit.setCategory(category);
        productService.save(produit).ifPresent(prod -> System.out.println("Produit enregistré: " + prod.getDesignation()));

        em.getTransaction().commit();

//      récuper un produit en utilisant le id
        productService.get(produit.getId()).map(entities.Product::getDesignation).ifPresentOrElse(nom -> System.out.println("Produit trouvé: " + nom), () -> System.out.println("Produit non trouvé"));
//      récuperer toutes les categories
        System.out.println("Categories:");
        categoryService.getAll().stream().map(entities.Category::getNom).forEach(nom -> System.out.println("- " + nom));
//      récuperer tous les produits
        System.out.println("Produits:");
        productService.getAll().stream().forEach(prod -> System.out.printf("- %s: %.2f€%n", prod.getDesignation(), prod.getPrix()));
//      récupérer un produit en utilisant un id
        productService.get(produit.getId()).ifPresent(prod -> {
            em.getTransaction().begin();
            productService.delete(prod);
            em.getTransaction().commit();
            System.out.println("Produit Supprimé: " + prod.getDesignation());
        });
        em.getTransaction().commit();

//      mise à jour d'une categorie
        category.setNom("Nourriture");
        categoryService.update(category).ifPresent(cat -> System.out.println("Categorie actualisée: " + cat.getNom()));
//      mise à jour du prix d'un produit
        produit.setPrix(74.99);
        productService.update(produit).ifPresent(prod -> System.out.printf("Produit actualisé: %s, Prix: %.2f€%n", prod.getDesignation(), prod.getPrix()));
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
