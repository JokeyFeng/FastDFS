package com.bg.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(FdfsClientConfig.class)
@SpringBootApplication
public class FastdfsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastdfsClientApplication.class, args);
	}
}
