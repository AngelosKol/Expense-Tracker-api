

CREATE OR REPLACE FUNCTION public.get_daily_costs(year_param character varying, month_param character varying)
 RETURNS TABLE(day integer, totalspent integer)
 LANGUAGE plpgsql
AS $function$
DECLARE
    month_number INT;
BEGIN
    -- Convert month name to month number
    SELECT CASE
        WHEN month_param ILIKE 'January' THEN 1
        WHEN month_param ILIKE 'February' THEN 2
        WHEN month_param ILIKE 'March' THEN 3
        WHEN month_param ILIKE 'April' THEN 4
        WHEN month_param ILIKE 'May' THEN 5
        WHEN month_param ILIKE 'June' THEN 6
        WHEN month_param ILIKE 'July' THEN 7
        WHEN month_param ILIKE 'August' THEN 8
        WHEN month_param ILIKE 'September' THEN 9
        WHEN month_param ILIKE 'October' THEN 10
        WHEN month_param ILIKE 'November' THEN 11
        WHEN month_param ILIKE 'December' THEN 12
        ELSE NULL
    END INTO month_number;

    IF month_number IS NULL THEN
        RAISE EXCEPTION 'Invalid month name: %', month_param;
    END IF;

    RETURN QUERY
    WITH days_in_month AS (
        SELECT generate_series(
            TO_DATE(year_param || '-' || month_number || '-01', 'YYYY-MM-DD'),
            TO_DATE(year_param || '-' || month_number || '-01', 'YYYY-MM-DD') + INTERVAL '1 month' - INTERVAL '1 day',
            INTERVAL '1 day'
        ) AS day
    )
    SELECT
        EXTRACT(DAY FROM days_in_month.day)::INTEGER AS day,
        COALESCE(SUM(td.price * td.quantity), 0)::INT AS totalSpent  -- Explicitly cast to INT
    FROM
        days_in_month
    LEFT JOIN
        "transaction" t ON TO_CHAR(t."date", 'YYYY-MM-DD') = TO_CHAR(days_in_month.day, 'YYYY-MM-DD')
    LEFT JOIN
        transaction_details td ON t.id = td.transaction_id
    GROUP BY
        days_in_month.day
    ORDER BY
        days_in_month.day;
END;
$function$
;


-- DROP FUNCTION public.get_daily_costs_with_shop(varchar, varchar);

CREATE OR REPLACE FUNCTION public.get_daily_costs_with_shop(year_param character varying, month_param character varying)
 RETURNS TABLE(day integer, shop_name character varying, totalspent integer)
 LANGUAGE plpgsql
AS $function$
DECLARE
    month_number VARCHAR;
BEGIN
    -- Convert month name or abbreviation to month number
    SELECT CASE
        WHEN month_param ILIKE 'January'  THEN '01'
        WHEN month_param ILIKE 'February'  THEN '02'
        WHEN month_param ILIKE 'March'  THEN '03'
        WHEN month_param ILIKE 'April' THEN '04'
        WHEN month_param ILIKE 'May' THEN '05'
        WHEN month_param ILIKE 'June'  THEN '06'
        WHEN month_param ILIKE 'July'  THEN '07'
        WHEN month_param ILIKE 'August' THEN '08'
        WHEN month_param ILIKE 'September'  THEN '09'
        WHEN month_param ILIKE 'October'  THEN '10'
        WHEN month_param ILIKE 'November'  THEN '11'
        WHEN month_param ILIKE 'December'  THEN '12'
        ELSE NULL
    END INTO month_number;

    IF month_number IS NULL THEN
        RAISE EXCEPTION 'Invalid month name: %', month_param;
    END IF;

    RETURN QUERY
    WITH days_in_month AS (
        SELECT generate_series(
            TO_DATE(year_param || '-' || month_number || '-01', 'YYYY-MM-DD'),
            TO_DATE(year_param || '-' || month_number || '-01', 'YYYY-MM-DD') + INTERVAL '1 month' - INTERVAL '1 day',
            INTERVAL '1 day'
        ) AS day
    )
    SELECT
        EXTRACT(DAY FROM days_in_month.day)::INTEGER AS day,
        s.name AS shop_name,
        COALESCE(SUM(td.price * td.quantity), 0)::INT AS totalSpent
    FROM
        days_in_month
    LEFT JOIN
        "transaction" t ON TO_CHAR(t."date", 'YYYY-MM-DD') = TO_CHAR(days_in_month.day, 'YYYY-MM-DD')
    LEFT JOIN
        transaction_details td ON t.id = td.transaction_id
    LEFT JOIN
        shop s ON t.shop_id = s.id
    GROUP BY
        days_in_month.day, s.name
    ORDER BY
        days_in_month.day, s.name;
END;
$function$
;


-- DROP FUNCTION public.get_year_costs(int4);

CREATE OR REPLACE FUNCTION public.get_year_costs(year_param integer)
 RETURNS TABLE(month character varying, totalspent integer)
 LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY
    SELECT
        TO_CHAR(month_series, 'Month')::VARCHAR AS month,
        COALESCE(SUM(td.price * td.quantity), 0)::INT AS totalSpent
    FROM
        generate_series(
            TO_DATE(year_param::TEXT || '-01-01', 'YYYY-MM-DD'),
            TO_DATE(year_param::TEXT || '-12-31', 'YYYY-MM-DD'),
            '1 month'::interval
        ) AS month_series
    LEFT JOIN
        "transaction" t ON TO_CHAR(t."date", 'YYYY-MM') = TO_CHAR(month_series, 'YYYY-MM')
    LEFT JOIN
        transaction_details td ON t.id = td.transaction_id
    GROUP BY
        month_series
    ORDER BY
        month_series;
END;
$function$
;