CREATE TABLE transactions (
                              transactions_id SERIAL PRIMARY KEY,
                              organisation_number VARCHAR(50) NOT NULL,
                              submitter_tin VARCHAR(50) NOT NULL,
                              category VARCHAR(50) NOT NULL,
                              taxation_year INTEGER NOT NULL,
                              taxation_period_type VARCHAR(50) NOT NULL,
                              time_of_submission TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              created TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                              status VARCHAR(50) NOT NULL,
                              vat_lines JSONB NOT NULL
);