import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './swich.reducer';

export const SwichDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const swichEntity = useAppSelector(state => state.swich.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="swichDetailsHeading">Swich</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{swichEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{swichEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{swichEntity.model}</dd>
          <dt>
            <span id="portNumber">Port Number</span>
          </dt>
          <dd>{swichEntity.portNumber}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{swichEntity.info}</dd>
          <dt>Swich Type</dt>
          <dd>{swichEntity.swichType ? swichEntity.swichType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/swich" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/swich/${swichEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SwichDetail;
