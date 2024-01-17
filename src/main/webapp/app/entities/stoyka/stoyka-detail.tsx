import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stoyka.reducer';

export const StoykaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stoykaEntity = useAppSelector(state => state.stoyka.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stoykaDetailsHeading">Stoyka</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stoykaEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{stoykaEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{stoykaEntity.info}</dd>
          <dt>Stoyka Type</dt>
          <dd>{stoykaEntity.stoykaType ? stoykaEntity.stoykaType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stoyka" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stoyka/${stoykaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StoykaDetail;
