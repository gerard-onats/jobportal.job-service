package com.jobportal.jobservice.fulltext;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.SqlTypes;

import java.util.List;

public class FullTextSQLFunction extends StandardSQLFunction {
    private static final BasicTypeReference<Boolean> RETURN_TYPE = new BasicTypeReference<>("boolean", Boolean.class, SqlTypes.BOOLEAN);

    FullTextSQLFunction(final String functionName) {
        super(functionName, true, RETURN_TYPE);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> arguments, SqlAstTranslator<?> translator) {
        sqlAppender.append("to_tsvector(");
        for(int i = 0; i < arguments.size() - 1; i++) {
            if(i != 0) sqlAppender.append(" || ");
            arguments.get(i).accept(translator);
        }
        sqlAppender.append(") @@ to_tsquery(");
        arguments.get(arguments.size() - 1).accept(translator);
        sqlAppender.append(")");
    }
}
