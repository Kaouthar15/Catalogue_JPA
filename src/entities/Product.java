package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "produit")
@NamedQuery(name = "Produit.getAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private Double prix;
    private Long quantite;
    private Long sdr;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Category category;


    private static final long serialVersionUID = 1L;

    public Product() {
        super();
    }



    public Product(String designation, Long id, Double prix, Long quantite, Long sdr, Category category) {
        this.designation = designation;
        this.prix = prix;
        this.quantite = quantite;
        this.sdr = sdr;
        this.category = category;
    }

    public Product(String smartphone, double v, long l, long l1, Category category) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getSdr() {
        return sdr;
    }

    public void setSdr(Long sdr) {
        this.sdr = sdr;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                ", sdr=" + sdr +
                ", categorie=" + (category != null ? category.getNom() : "null") +
                '}';
    }

    public int getPrice() {
        return 0;
    }


}
