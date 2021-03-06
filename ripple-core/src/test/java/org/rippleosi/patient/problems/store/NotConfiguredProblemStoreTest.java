/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.rippleosi.patient.problems.store;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.rippleosi.common.exception.ConfigurationException;
import org.rippleosi.common.types.RepoSourceTypes;

/**
 */
public class NotConfiguredProblemStoreTest {

    private NotConfiguredProblemStore problemStore;

    @Before
    public void setUp() throws Exception {
        problemStore = new NotConfiguredProblemStore();
    }

    @Test
    public void shouldReportAsNotConfiguredImplementation() {
        assertEquals(RepoSourceTypes.NONE, problemStore.getSource());
    }

    @Test(expected = ConfigurationException.class)
    public void shouldThrowExceptionWhenTryingCreateProblem() {
        problemStore.create(null, null);
    }

    @Test(expected = ConfigurationException.class)
    public void shouldThrowExceptionWhenTryingToUpdateProblem() {
        problemStore.update(null, null);
    }
}
