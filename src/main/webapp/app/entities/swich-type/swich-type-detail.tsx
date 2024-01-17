import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './swich-type.reducer';

export const SwichTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const swichTypeEntity = useAppSelector(state => state.swichType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="swichTypeDetailsHeading">Swich Type</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{swichTypeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{swichTypeEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{swichTypeEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{swichTypeEntity.obyekt ? swichTypeEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/swich-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/swich-type/${swichTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SwichTypeDetail;
