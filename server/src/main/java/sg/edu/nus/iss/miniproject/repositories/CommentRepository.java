package sg.edu.nus.iss.miniproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.Comment;

@Repository
public class CommentRepository {
    
    @Autowired
    private MongoTemplate template;

    // db.comments.insert({
		// title:title,
		// name:name,
        // email:email,
        // subject:subject,
		// rating:rating,
		// comment:comment
	// })

    private static final String COMMENTS_COL = "comments";

    public Comment insertComment (Comment r) {
        return template.insert(r, COMMENTS_COL);
    }
}
