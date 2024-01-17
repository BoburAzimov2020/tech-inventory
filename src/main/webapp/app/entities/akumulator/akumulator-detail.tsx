import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './akumulator.reducer';

export const AkumulatorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const akumulatorEntity = useAppSelector(state => state.akumulator.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="akumulatorDetailsHeading">Akumulator</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{akumulatorEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{akumulatorEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{akumulatorEntity.model}</dd>
          <dt>
            <span id="power">Power</span>
          </dt>
          <dd>{akumulatorEntity.power}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{akumulatorEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{akumulatorEntity.obyekt ? akumulatorEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/akumulator" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/akumulator/${akumulatorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AkumulatorDetail;
