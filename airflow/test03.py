from airflow import DAG
from airflow.operators.bash_operator import BashOperator
from datetime import datetime, timedelta


default_args = {
    'owner': 'root',
    'depends_on_past': False,
    'start_date': datetime(2019, 8, 2),
    #'email': ['airflow@example.com'],
    #'email_on_failure': False,
    #'email_on_retry': False,
    #'retries': 1,
    #'retry_delay': timedelta(minutes=5),
    # 'queue': 'bash_queue',
    # 'pool': 'backfill',
    # 'priority_weight': 10,
    # 'end_date': datetime(2016, 1, 1),
}

dag = DAG('test03', default_args=default_args, schedule_interval='35 16 * * *')

t1 = BashOperator(
    task_id='print_date',
    bash_command='ssh houda "sh "',
    dag=dag)

t2 = BashOperator(
    task_id='sleep',
    bash_command='sleep 5',
    retries=3,
    dag=dag)


t3 = BashOperator(
    task_id='templated',
    bash_command='date',
    dag=dag)


t2 >> t1
t1 >> t3
