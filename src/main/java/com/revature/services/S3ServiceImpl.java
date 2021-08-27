package com.revature.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Sprint;
import com.revature.util.S3Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3ServiceImpl implements S3Service {

	private S3Client s3;
	private static final Logger log = LogManager.getLogger(S3ServiceImpl.class);

	@Autowired
	public S3ServiceImpl(S3Client s3) {
		super();
		this.s3 = s3;
	}

	@Override
	public Mono<Void> uploadToBucket(String key, Object o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(ObjectOutputStream oos= new ObjectOutputStream(bos)){
			oos.writeObject(o);
			oos.flush();
			byte[] data = bos.toByteArray();
			s3.putObject(PutObjectRequest.builder().bucket(S3Util.BUCKET_NAME).key(key).build(),
					RequestBody.fromBytes(data));
		} catch (IOException e) {
			log.error(e);
		}
		return Mono.empty();
	}

	@Override
	public Flux<Sprint> getSprints(String... keys) {
		List<Sprint> sprints = new ArrayList<>();
		for(String key : keys) {
			try {
				InputStream object = s3.getObject(GetObjectRequest.builder().bucket(S3Util.BUCKET_NAME).key(key).build());
				Sprint sprint = (Sprint)new ObjectInputStream(object).readObject();
				sprints.add(sprint);
				} catch (ClassNotFoundException | IOException e) {
					log.error(e);
				}
		}
		return Flux.fromStream(sprints.stream());
	}

}
