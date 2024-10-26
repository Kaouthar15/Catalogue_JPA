package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
@NamedQuery(name = "Category.getAll", query = "SELECT p FROM Category p")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String Description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> produits;


    public Category(Long id, String description, String nom) {
        Description = description;
        this.nom = nom;
    }

    public Category() {

    }

    public Category(String newTitre, String newDescription) {
    }

    public List<Product> getProduits() {
        return produits;
    }

    public void setProduits(List<Product> produits) {
        this.produits = produits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
