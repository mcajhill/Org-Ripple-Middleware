/*
 *  Copyright 2015 Ripple OSI
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.rippleosi.common.model;

import java.util.List;
import java.util.Map;

/**
 */
public class EtherCISQueryResponse {

    private Meta meta;
    private String sql;
    private String executedSql;
    private Map<String, Object> aqlParameters;
    private List<Map<String, Object>> resultSet;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getExecutedSql() {
        return executedSql;
    }

    public void setExecutedSql(String executedSql) {
        this.executedSql = executedSql;
    }

    public Map<String, Object> getAqlParameters() {
        return aqlParameters;
    }

    public void setAqlParameters(Map<String, Object> aqlParameters) {
        this.aqlParameters = aqlParameters;
    }

    public List<Map<String, Object>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<Map<String, Object>> resultSet) {
        this.resultSet = resultSet;
    }
}
