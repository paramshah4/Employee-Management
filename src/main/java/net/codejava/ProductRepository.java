package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByNameLike(String name);
}
