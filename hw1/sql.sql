with base_v as(
	select cust,max(quant) max_q,min(quant) min_q,avg(quant) avg_q
	from sales
	group by cust
),
max_v as(
	select b.cust, b.max_q, s.prod, s.month, s.day, s.year, s.state, b.min_q, b.avg_q
	from base_v b, sales s
	where b.cust = s.cust and
	      b.max_q = s.quant
)
--------------main-----------------------
select m.cust, m.max_q, m.prod, m.month, m.day, m.year, m.state, m.min_q, s.prod, s.month, s.day, s.year, s.state,m.avg_q
from max_v m, sales s
where m.cust = s.cust and 
      m.min_q = s.quant

	  
--------------------------------------------------------------------------------------------------------------------------
	  
	  
with jan_max as(
select cust, prod, max(quant) as jan_max
from sales
where year between 2000 and 2005 
and month = 1
group by cust, prod
),
F_Jan as(
select j.cust, j.prod, j.jan_max, s.month jan_month, s.day jan_day, s.year jan_year
from jan_max j, sales s
where j.cust = s.cust
and j.prod = s.prod
and j.jan_max = s.quant
order by cust,prod
),
feb_min as(
select cust, prod, min(quant) as feb_min
from sales
where month = 2
group by cust, prod
),
F_Feb as(
select f.cust, f.prod, f.feb_min, s.month feb_month, s.day feb_day, s.year feb_year
from feb_min f, sales s
where f.cust = s.cust
and f.prod = s.prod
and f.feb_min = s.quant
),
mar_min as(
select cust, prod, min(quant) as mar_min
from sales
where month = 3
group by cust, prod
),
F_Mar as(
select m.cust, m.prod, m.mar_min, s.month mar_month, s.day mar_day, s.year mar_year
from mar_min m, sales s
where m.cust = s.cust
and m.prod = s.prod
and m.mar_min = s.quant
order by cust, prod
)
---------main------------------------
select * 
from F_Jan natural full outer join F_Feb natural full outer join F_Mar
order by cust, prod
