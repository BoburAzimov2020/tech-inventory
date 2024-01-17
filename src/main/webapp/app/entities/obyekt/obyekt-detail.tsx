import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './obyekt.reducer';

export const ObyektDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const obyektEntity = useAppSelector(state => state.obyekt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="obyektDetailsHeading">Obyekt</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{obyektEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{obyektEntity.name}</dd>
          <dt>
            <span id="home">Home</span>
          </dt>
          <dd>{obyektEntity.home}</dd>
          <dt>
            <span id="street">Street</span>
          </dt>
          <dd>{obyektEntity.street}</dd>
          <dt>
            <span id="latitude">Latitude</span>
          </dt>
          <dd>{obyektEntity.latitude}</dd>
          <dt>
            <span id="longitude">Longitude</span>
          </dt>
          <dd>{obyektEntity.longitude}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{obyektEntity.info}</dd>
          <dt>Buyurtma Raqam</dt>
          <dd>{obyektEntity.buyurtmaRaqam ? obyektEntity.buyurtmaRaqam.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/obyekt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/obyekt/${obyektEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ObyektDetail;
