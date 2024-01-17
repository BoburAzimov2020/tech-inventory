import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stabilizator.reducer';

export const StabilizatorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stabilizatorEntity = useAppSelector(state => state.stabilizator.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stabilizatorDetailsHeading">Stabilizator</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stabilizatorEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{stabilizatorEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{stabilizatorEntity.model}</dd>
          <dt>
            <span id="power">Power</span>
          </dt>
          <dd>{stabilizatorEntity.power}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{stabilizatorEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{stabilizatorEntity.obyekt ? stabilizatorEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stabilizator" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stabilizator/${stabilizatorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StabilizatorDetail;
