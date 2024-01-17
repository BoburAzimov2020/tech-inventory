import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stoyka-type.reducer';

export const StoykaTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stoykaTypeEntity = useAppSelector(state => state.stoykaType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stoykaTypeDetailsHeading">Stoyka Type</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stoykaTypeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{stoykaTypeEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{stoykaTypeEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{stoykaTypeEntity.obyekt ? stoykaTypeEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stoyka-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stoyka-type/${stoykaTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StoykaTypeDetail;
