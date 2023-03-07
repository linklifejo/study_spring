--사원관리

select * from employees
where department_id=60;


select e.*, job_title, department_name
		, last_name || ' ' || first_name name
from departments d right outer join employees e
			on e.department_id = d.department_id
inner join jobs j on j.job_id = e.job_id
where e.department_id is null
;



--사원이 소속되어 있는 부서목록
select department_id from employees
group by department_id
order by 1;

-- department_name 에  null인 경우 소속없음 으로 조회되게
-- null 인 값을 다른 값으로 대체하여 조회
-- nvl(대상, null인경우반환값)
-- nvl2(대상, null이아닌경우 반환값, null인경우반환값)
-- coalesce 
select distinct department_id, nvl(department_name, '소속없음') department_name
, nvl2(department_name, '소속'|| department_name ||'있음', '소속없음') dept1
, coalesce(department_name, '소속있음', '소속없음') dept2
--, coalesce(휴대전화, 사무실전화, 집전화, 긴급전화) dept2
from employees e left outer join departments d using(department_id)
order by 1;

select distinct department_id, nvl(department_name, '소속없음') department_name
from employees e left outer join departments d using(department_id)
order by 1

