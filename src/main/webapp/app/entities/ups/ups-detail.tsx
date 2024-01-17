import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ups.reducer';

export const UpsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const upsEntity = useAppSelector(state => state.ups.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="upsDetailsHeading">Ups</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{upsEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{upsEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{upsEntity.model}</dd>
          <dt>
            <span id="power">Power</span>
          </dt>
          <dd>{upsEntity.power}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{upsEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{upsEntity.obyekt ? upsEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ups" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ups/${upsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UpsDetail;
