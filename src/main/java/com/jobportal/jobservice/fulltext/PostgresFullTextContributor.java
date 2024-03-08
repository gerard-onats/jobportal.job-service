package com.jobportal.jobservice.fulltext;

import com.jobportal.jobservice.fulltext.FullTextSQLFunction;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;

public class PostgresFullTextContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry()
                .register("fts", new FullTextSQLFunction("fts"));
    }
}
