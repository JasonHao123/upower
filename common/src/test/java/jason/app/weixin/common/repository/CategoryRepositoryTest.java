package jason.app.weixin.common.repository;

import jason.app.weixin.common.BaseTestCase;
import jason.app.weixin.common.entity.CategoryImpl;

import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Ignore
public class CategoryRepositoryTest extends BaseTestCase {

    @Autowired
    CategoryRepository repository;


    
    private void doInsert() {
        String categoryName = "jason";
        CategoryImpl post = new CategoryImpl();
        post.setName(categoryName);
        repository.save(post);
    }
    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testInsertAndFindOne() {
        String categoryName = "jason";
        CategoryImpl post = new CategoryImpl();
        post.setName(categoryName);
        post =repository.saveAndFlush(post);

        CategoryImpl dbpost = repository.findOne(post.getId());
        assertNotNull(dbpost);
        assertEquals(categoryName, dbpost.getName());

    }

    @Test
    public void testFindAll() {
        List<CategoryImpl> result = repository.findAll();
        assertNotNull(result);
        assertEquals(7, result.size());
    }

    @Test
    public void testFindByTypeAndNameLikeFound() {
        List<CategoryImpl> result = repository.findByTypeAndNameLikeIgnoreCase("feature", "%hell%");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindByTypeAndNameLikeNotFound() {
        List<CategoryImpl> result = repository.findByTypeAndNameLikeIgnoreCase("feature", "%jason%");
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        // TODO Auto-generated method stub
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/category/categories.xml"));

    }

}
