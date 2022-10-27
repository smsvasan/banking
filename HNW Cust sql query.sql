select customer_name, net_worth from customer_master cust inner join 
((select top 3 customer_id, sum(account_balance) as net_worth from account_master group by customer_id order by sum(account_balance) desc) as highnetworthcust)
on cust.customer_id = highnetworthcust.customer_id