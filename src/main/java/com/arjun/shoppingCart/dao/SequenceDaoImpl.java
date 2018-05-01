package com.arjun.shoppingCart.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.arjun.shoppingCart.SpringMongoConfig;
import com.arjun.shoppingCart.exception.SequenceException;
import com.arjun.shoppingCart.models.SequenceId;

@Repository
public class SequenceDaoImpl implements SequenceDAO {


	private ApplicationContext ctx;

	@Override
	public long getNextSequenceId(String key) throws SequenceException {
		ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

		// get sequence id
		Query query = new Query(Criteria.where("_id").is(key));

		// increase sequence id by 1
		Update update = new Update();
		update.inc("seq", 1);

		// return new increased id
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		// this is the magic happened.
		SequenceId seqId = mongoOperations.findAndModify(query, update,	options, SequenceId.class);

		// if no id, throws SequenceException
		// optional, just a way to tell user when the sequence id is failed to
		// generate.
		if (seqId == null) {
			throw new SequenceException("Unable to get sequence id for key : "
					+ key);
		}

		return seqId.getSeq();

	}

}
